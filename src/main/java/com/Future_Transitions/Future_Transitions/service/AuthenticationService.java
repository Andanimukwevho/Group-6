package com.Future_Transitions.Future_Transitions.service;

import com.Future_Transitions.Future_Transitions.dto.*;

public interface AuthenticationService {

    RequestResponse register(RegisterDTO registerDTO);

    LoginResponse login(LoginDTO loginDTO);

    RequestResponse refreshToken(RefreshedTokenRequest refreshedTokenRequest);

}

