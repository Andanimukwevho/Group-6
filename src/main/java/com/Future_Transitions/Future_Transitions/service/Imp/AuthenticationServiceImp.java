package com.Future_Transitions.Future_Transitions.service.Imp;


import com.Future_Transitions.Future_Transitions.dto.*;
import com.Future_Transitions.Future_Transitions.model.Application;
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public LoginResponse login(LoginDTO LoginDTO) {
        LoginResponse response = new LoginResponse();

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(LoginDTO.getEmail(), LoginDTO.getPassword()));
            var user = userRepository.findByEmail(LoginDTO.getEmail()).orElseThrow();
            var jwt = jwtServiceImp.generateToken(user);
            var refreshedToken = jwtServiceImp.generateRefreshToken(new HashMap<>(), user);


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

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    // we validate and generate new token
//    public RequestResponse refreshToken(RefreshedTokenRequest refreshTokenRequest) {
//        String token = refreshTokenRequest.getRefreshedToken();

    public RequestResponse refreshToken(RefreshedTokenRequest refreshTokenRequest) {
        RequestResponse response = new RequestResponse();
        try {
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

        } catch (Exception e) {
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

    public RequestResponse updateUser(String email, User updatedUser, boolean isFullUpdate) {
        RequestResponse reqRes = new RequestResponse();
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();

                //updates for all fields
                if (isFullUpdate) {
                    existingUser.setName(updatedUser.getName());
                    existingUser.setSurname(updatedUser.getSurname());
                    existingUser.setAddress(updatedUser.getAddress());
                    existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
                    existingUser.setAge(updatedUser.getAge());
                    existingUser.setProvince(updatedUser.getProvince());
                } else {
                    // for update that only for few fields not all
                    if (updatedUser.getName() != null) existingUser.setName(updatedUser.getName());
                    if (updatedUser.getSurname() != null) existingUser.setSurname(updatedUser.getSurname());
                    if (updatedUser.getAddress() != null) existingUser.setAddress(updatedUser.getAddress());
                    if (updatedUser.getPhoneNumber() != null) existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
                    if (updatedUser.getAge() != null) existingUser.setAge(updatedUser.getAge());
                    if (updatedUser.getProvince() != null) existingUser.setProvince(updatedUser.getProvince());

                }

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

    public UserDTO mapUserToDTO(User user) {
        UserDTO dto = new UserDTO();

        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setAddress(user.getAddress());
        dto.setProvince(user.getProvince());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());

        if (user.getApplications() != null) {
            List<ApplicationDTO> appliedJobs = user.getApplications().stream()
                    .map(this::mapApplicationToDTO)
                    .collect(Collectors.toList());
            dto.setAppliedJobs(appliedJobs);
        }

        return dto;
    }

    private ApplicationDTO mapApplicationToDTO(Application application) {
        ApplicationDTO dto = new ApplicationDTO();

        dto.setId(application.getId());
        dto.setJobId(application.getJobId());
        dto.setApplicationName(application.getApplicationName());
        dto.setStatus(application.getStatus());
        dto.setCvPath(application.getCvPath());
        dto.setCoverLetterPath(application.getCoverLetterPath());
        dto.setIdDocumentPath(application.getIdDocumentPath());
        dto.setAppliedDate(application.getAppliedDate());

        User applicant  = application.getApplicant();
        if (applicant  != null) {
            dto.setName(applicant .getName());
            dto.setSurname(applicant .getSurname());
            dto.setEmail(applicant .getEmail());
            dto.setPhoneNumber(applicant .getPhoneNumber());
            dto.setAddress(applicant .getAddress());
            dto.setAge(applicant .getAge());
            dto.setProvince(applicant .getProvince());
        }

        return dto;
    }

    public RequestResponse getMyInfo(String email) {
        RequestResponse reqRes = new RequestResponse();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            UserDTO userDTO = mapUserToDTO(user);
            reqRes.setUserDTO(userDTO);
            reqRes.setStatusCode(200);
            reqRes.setMessage("User profile retrieved successfully");

        } catch (NoSuchElementException e) {
            reqRes.setStatusCode(404);
            reqRes.setMessage(e.getMessage());
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;
    }
}




