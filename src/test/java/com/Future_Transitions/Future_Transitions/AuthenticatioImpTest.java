package com.Future_Transitions.Future_Transitions;


import com.Future_Transitions.Future_Transitions.dto.LoginDTO;
import com.Future_Transitions.Future_Transitions.dto.LoginResponse;
import com.Future_Transitions.Future_Transitions.dto.RegisterDTO;
import com.Future_Transitions.Future_Transitions.dto.RequestResponse;
import com.Future_Transitions.Future_Transitions.model.Province;
import com.Future_Transitions.Future_Transitions.model.Role;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.repository.UserRepository;
import com.Future_Transitions.Future_Transitions.service.Imp.AuthenticationServiceImp;
import com.Future_Transitions.Future_Transitions.service.JWTServiceImp;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticatioImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTServiceImp jwtServiceImp;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImp authenticationServiceImp;

    @Test
    public void testRegister_SuccessfulRegistration() {

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setRole(Role.USER);

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setName("John");
        registerDTO.setSurname("Doe");
        registerDTO.setAddress("123 Main St");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setEmail("john@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setAge(30);
        registerDTO.setProvince(Province.MPUMALANGA);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(registerDTO.getName());
        savedUser.setSurname(registerDTO.getSurname());
        savedUser.setAddress(registerDTO.getAddress());
        savedUser.setPhoneNumber(registerDTO.getPhoneNumber());
        savedUser.setEmail(registerDTO.getEmail());
        savedUser.setPassword("encoded_password"); // Simulated encoded password
        savedUser.setRole(Role.USER);
        savedUser.setAge(registerDTO.getAge());
        savedUser.setProvince(registerDTO.getProvince());

        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtServiceImp.generateToken(any(User.class))).thenReturn("mock_jwt_token");
        when(jwtServiceImp.generateRefreshToken(any(HashMap.class), any(UserDetails.class))).thenReturn("mock_refresh_token");

        RequestResponse response = authenticationServiceImp.register(registerDTO);

        assertEquals(200, response.getStatusCode());
        assertEquals("User registered successfully", response.getMessage());
        assertNotNull(response.getUser());
        assertEquals("John", response.getUser().getName());
        assertEquals("mock_jwt_token", response.getToken());
        assertEquals("mock_refresh_token", response.getRefreshToken());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals("John", capturedUser.getName());
        assertEquals("encoded_password", capturedUser.getPassword());
        assertEquals(Role.USER, capturedUser.getRole());

        verify(passwordEncoder).encode("password123");
        verify(jwtServiceImp).generateToken(any(UserDetails.class));
        verify(jwtServiceImp).generateRefreshToken(any(HashMap.class), any(User.class));
    }
    @Test
    public void testLogin_SuccessfulLogin() throws Exception {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("user@example.com");
        loginDTO.setPassword("password");

        User user = new User();
        user.setEmail(loginDTO.getEmail());
        user.setRole(Role.USER);

        when(userRepository.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        when(jwtServiceImp.generateToken(user)).thenReturn("mock_jwt_token");
        when(jwtServiceImp.generateRefreshToken(any(HashMap.class), eq(user))).thenReturn("mock_refresh_token");

        LoginResponse response = authenticationServiceImp.login(loginDTO);

        assertEquals(200, response.getStatusCode());
        assertEquals("Successfully Logged In", response.getMessage());
        assertEquals("mock_jwt_token", response.getToken());
        assertEquals("mock_refresh_token", response.getRefreshToken());
        assertEquals(Role.USER, response.getRole());
        assertEquals("24Hrs", response.getExpirationTime());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(loginDTO.getEmail());
        verify(jwtServiceImp).generateToken(user);
        verify(jwtServiceImp).generateRefreshToken(any(HashMap.class), eq(user));
    }

    @Test
    public void testLogin_FailedAuthentication() throws Exception {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("user@example.com");
        loginDTO.setPassword("wrong_password");

        doThrow(new RuntimeException("Bad credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        LoginResponse response = authenticationServiceImp.login(loginDTO);

        assertEquals(500, response.getStatusCode());
        assertEquals("Bad credentials", response.getMessage());
        assertNull(response.getToken());
        assertNull(response.getRefreshToken());
        assertNull(response.getRole());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByEmail(anyString());
        verify(jwtServiceImp, never()).generateToken(any());
        verify(jwtServiceImp, never()).generateRefreshToken(any(), any());
    }
}

