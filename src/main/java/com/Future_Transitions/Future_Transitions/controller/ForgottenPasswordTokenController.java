package com.Future_Transitions.Future_Transitions.controller;

import com.Future_Transitions.Future_Transitions.model.ForgottenPasswordToken;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.repository.ForgottenPasswordTokenRepository;
import com.Future_Transitions.Future_Transitions.repository.UserRepository;
import com.Future_Transitions.Future_Transitions.service.EmailService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ForgottenPasswordTokenController {


    private final PasswordEncoder passwordEncoder;
    private final ForgottenPasswordTokenRepository forgottenPasswordTokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Value("${app.reset-password.url}")
    private String resetPasswordUrl;

    @PersistenceContext
    private EntityManager entityManager;

    public ForgottenPasswordTokenController(PasswordEncoder passwordEncoder, ForgottenPasswordTokenRepository forgottenPasswordTokenRepository, UserRepository userRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.forgottenPasswordTokenRepository = forgottenPasswordTokenRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @PostMapping("/forgot-password")
    @Transactional
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.ok("If that email exists, a reset link has been sent.");
        }

        User user = userOpt.get();

        // deleting the old token
        forgottenPasswordTokenRepository.deleteByUser(user);
        entityManager.flush();   // Force DB update
        entityManager.clear();   // Clear persistence context

        // this create a new token
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        ForgottenPasswordToken resetToken = new ForgottenPasswordToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(expiryDate);

        // saving the new token
        forgottenPasswordTokenRepository.save(resetToken);

        // sending the email
        String resetUrl = resetPasswordUrl + "?token=" + token;

        emailService.sendEmail(
                user.getEmail(),
                "Password Reset Request",
                "Click the link below to reset your password:\n\n" + resetUrl
        );

        return ResponseEntity.ok("If that email exists, a reset link has been sent.");
    }

    @Transactional
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<ForgottenPasswordToken> tokenOpt = forgottenPasswordTokenRepository.findByToken(token);

        if (tokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token.");
        }

        ForgottenPasswordToken resetToken = tokenOpt.get();

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expired.");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        forgottenPasswordTokenRepository.delete(resetToken);

        return ResponseEntity.ok("Password reset successful.");
    }
}








