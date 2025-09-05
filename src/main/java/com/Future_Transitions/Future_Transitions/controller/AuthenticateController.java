package com.Future_Transitions.Future_Transitions.controller;


import com.Future_Transitions.Future_Transitions.dto.LoginDTO;
import com.Future_Transitions.Future_Transitions.dto.RefreshedTokenRequest;
import com.Future_Transitions.Future_Transitions.dto.RegisterDTO;
import com.Future_Transitions.Future_Transitions.dto.RequestResponse;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.service.Imp.AuthenticationServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/auth")
@CrossOrigin("*")

@RestController
public class AuthenticateController {

    private final AuthenticationServiceImp authenticationServiceImp;

    public AuthenticateController(AuthenticationServiceImp authenticationServiceImp) {
        this.authenticationServiceImp = authenticationServiceImp;
    }

    @PostMapping("/register")
    public ResponseEntity<RequestResponse> register(@Valid @RequestBody RegisterDTO registerDTO){
       return ResponseEntity.ok(authenticationServiceImp.register(registerDTO));
   }
   @PostMapping("/login")
   public ResponseEntity<RequestResponse> login(@RequestBody LoginDTO loginDTO){
       return ResponseEntity.ok(authenticationServiceImp.login(loginDTO));
   }
   @PostMapping("/refresh")
   public ResponseEntity<RequestResponse> refreshToken(@RequestBody RefreshedTokenRequest refreshedTokenRequest){
       return ResponseEntity.ok(authenticationServiceImp.refreshToken(refreshedTokenRequest));
   }
   @GetMapping("/getalluser")
   public ResponseEntity<RequestResponse> getAllUsers(){
       return ResponseEntity.ok(authenticationServiceImp.getAllUsers());
   }
   @GetMapping("/useremail")
   public ResponseEntity<RequestResponse> getUserEmail(@PathVariable String email){
   return ResponseEntity.ok(authenticationServiceImp.getUsersByEmail(email));
   }
   @DeleteMapping("/delete")
   public ResponseEntity<RequestResponse> deleteUser(@PathVariable String email){
       return ResponseEntity.ok(authenticationServiceImp.deleteUser(email));
   }

    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<RequestResponse> getUSerByEmail(@PathVariable String email){
        return ResponseEntity.ok(authenticationServiceImp.getUsersByEmail(email));

    }

    @PutMapping("/admin/update/{userEmail}")
    public ResponseEntity<RequestResponse> updateUser(@PathVariable String email, @RequestBody User user){
        return ResponseEntity.ok(authenticationServiceImp.updateUser(email, user));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<RequestResponse> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        RequestResponse response = authenticationServiceImp.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
