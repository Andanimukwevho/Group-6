package com.Future_Transitions.Future_Transitions.service.Imp;


import com.Future_Transitions.Future_Transitions.dto.LoginDTO;
import com.Future_Transitions.Future_Transitions.dto.RefreshedTokenRequest;
import com.Future_Transitions.Future_Transitions.dto.RegisterDTO;
import com.Future_Transitions.Future_Transitions.dto.RequestResponse;
import com.Future_Transitions.Future_Transitions.model.Role;
import com.Future_Transitions.Future_Transitions.model.User;
import com.Future_Transitions.Future_Transitions.repository.UserRepository;
import com.Future_Transitions.Future_Transitions.service.AuthenticationService;
import com.Future_Transitions.Future_Transitions.service.JWTServiceImp;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class AuthenticationServiceImp implements AuthenticationService {

    private final UserRepository userRepository;
    private final JWTServiceImp jwtServiceImp;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;



    public AuthenticationServiceImp(UserRepository userRepository, JWTServiceImp jwtServiceImp, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtServiceImp = jwtServiceImp;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    public RequestResponse register(RegisterDTO registerDTO) {
        RequestResponse resp = new RequestResponse();

        // this create a user object so to get all the variables declared in User class one to
        // set them on User object and get them on ResponseRequest that create the register.
        try {

            User user = new User();

            user.setName(registerDTO.getName());
            user.setSurname(registerDTO.getSurname());
            user.setAddress(registerDTO.getAddress());
            user.setPhoneNumber(registerDTO.getPhoneNumber());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setRole(Role.USER);
            user.setAge(registerDTO.getAge());
            user.setProvince(registerDTO.getProvince());

            User userResult = userRepository.save(user);

            String jwt = jwtServiceImp.generateToken(user);
            String refreshToken = jwtServiceImp.generateRefreshToken(new HashMap<>(), user);

            resp.setStatusCode(200);
            resp.setMessage("User registered successfully");
            resp.setUser(userResult);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshToken);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatusCode(500);
            resp.setMessage("Registration failed: " + e.getMessage());
        }

        return resp;
    }



    public RequestResponse login(LoginDTO LoginDTO){
        RequestResponse response = new RequestResponse();

        try{
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(LoginDTO.getEmail(),LoginDTO.getPassword()));
        var user = userRepository.findByEmail(LoginDTO.getEmail()).orElseThrow();
        var jwt = jwtServiceImp.generateToken(user);
        var refreshedToken = jwtServiceImp.generateRefreshToken(new HashMap<>(),user);


            String roleName = user.getRole().name(); // e.g. "ADMIN" or "USER"
            List<String> roles = List.of("ROLE_" + roleName);

            String redirectUrl = switch (roleName) {
                case "ADMIN" -> "/admin/dashboard";
                case "USER" -> "/user/home";
                default -> "/unknown";

            };

        response.setStatusCode(200);
        response.setToken(jwt);
        response.setRole(user.getRole());
        response.setRefreshToken(refreshedToken);
        response.setExpirationTime("24Hrs");
        response.setMessage("Successfully Logged In");

    }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
    }
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

    public RequestResponse getAllUsers() {
        RequestResponse reqRes = new RequestResponse();

        try {
            List<User> result = userRepository.findAll();
            if (!result.isEmpty()) {
                reqRes.setUserList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


    public RequestResponse getUsersByEmail(String email) {
        RequestResponse reqRes = new RequestResponse();
        try {
            User usersById = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setUser(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with email '" + email + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public RequestResponse deleteUser(String email) {
        RequestResponse reqRes = new RequestResponse();
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found " + email));
                userRepository.deleteById(user.getId());

                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public RequestResponse updateUser(String email, User updatedUser) {
        RequestResponse reqRes = new RequestResponse();
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setSurname(updatedUser.getSurname());
                existingUser.setAddress(updatedUser.getAddress());
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setRole(updatedUser.getRole());


                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {

                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                User savedUser = userRepository.save(existingUser);
                reqRes.setUser(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


    public RequestResponse getMyInfo(String email){
        RequestResponse reqRes = new RequestResponse();
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setUser(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }
}




