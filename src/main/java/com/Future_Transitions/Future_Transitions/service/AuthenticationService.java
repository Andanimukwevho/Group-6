package com.Future_Transitions.Future_Transitions.service;

import com.Future_Transitions.Future_Transitions.dto.LoginDTO;
import com.Future_Transitions.Future_Transitions.dto.RefreshedTokenRequest;
import com.Future_Transitions.Future_Transitions.dto.RegisterDTO;
import com.Future_Transitions.Future_Transitions.dto.RequestResponse;
import com.Future_Transitions.Future_Transitions.model.Role;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@AllArgsConstructor
@Service
@Data
public class AuthenticationService {

    private UserRepository userRepository;
//    private JWTUtils jwtUtils;
    private JWTServiceImp jwtServiceImp;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;


    public RequestResponse register(RegisterDTO  registerDTO){
        RequestResponse resp = new RequestResponse();

    // this create a user object so to get all the variables declared in User class one to
            // set them on User object and get them on ResponseRequest that create the register.
           try{

            User user = new User();

            user.setName(registerDTO.getName());
            user.setSurname(registerDTO.getSurname());
            user.setPhoneNumber(registerDTO.getPhoneNumber());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(registerDTO.getPassword());
            user.setRole(Role.USER);

            User userResult = userRepository.save(user);

        String jwt = jwtServiceImp.generateToken(user);
        String refreshToken = jwtServiceImp.generateRefreshToken(new HashMap<>(), user);

        resp.setStatusCode(201);
        resp.setMessage("User registered successfully");
        resp.setUser(user);
        resp.setToken(jwt);
        resp.setRefreshToken(refreshToken);

    } catch (Exception e) {
        resp.setStatusCode(500);
        resp.setMessage("Registration failed: " + e.getMessage());
    }

        return resp;
}



    public RequestResponse login(LoginDTO LoginDTO){
        RequestResponse response = new RequestResponse();
        authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(LoginDTO.getEmail(),LoginDTO.getPassword()));
            var user = userRepository.findByEmail(LoginDTO.getEmail()).orElseThrow();
            var jwt = jwtServiceImp.generateToken(user);
            var refreshedToken = jwtServiceImp.generateRefreshToken(new HashMap<>(),user);

        return response;

    }
    // we validate and generate new token
//    public RequestResponse refreshToken(RefreshedTokenRequest refreshTokenRequest) {
//        String token = refreshTokenRequest.getRefreshedToken();

        public RequestResponse refreshToken(RefreshedTokenRequest refreshTokenRequest){
            RequestResponse response = new RequestResponse();
            try{
                String ourEmail = jwtServiceImp.extractUserName(refreshTokenRequest.getRefreshedToken());
                User users = userRepository.findByEmail(ourEmail).orElseThrow();
                if (jwtServiceImp.isTokenValid(refreshTokenRequest.getRefreshedToken(), users)) {
                    var jwt = jwtServiceImp.generateToken(users);
                    response.setStatusCode(200);
                    response.setToken(jwt);
                    response.setRefreshToken(refreshTokenRequest.getRefreshedToken());
                    response.setExpirationTime("24Hr");
                    response.setMessage("Successfully Refreshed Token");
                }
                response.setStatusCode(200);
                return response;

            }catch (Exception e){
                response.setStatusCode(500);
                response.setMessage(e.getMessage());
                return response;
            }
        }

    }


