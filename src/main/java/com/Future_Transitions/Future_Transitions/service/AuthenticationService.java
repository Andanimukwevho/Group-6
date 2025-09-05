package com.Future_Transitions.Future_Transitions.service;

import com.Future_Transitions.Future_Transitions.dto.LoginDTO;
import com.Future_Transitions.Future_Transitions.dto.RefreshedTokenRequest;
import com.Future_Transitions.Future_Transitions.dto.RegisterDTO;
import com.Future_Transitions.Future_Transitions.dto.RequestResponse;


public interface AuthenticationService {

    RequestResponse register(RegisterDTO registerDTO);

    RequestResponse login(LoginDTO loginDTO);

    RequestResponse refreshToken(RefreshedTokenRequest refreshedTokenRequest);

}

