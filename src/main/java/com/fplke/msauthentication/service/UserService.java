package com.fplke.msauthentication.service;

import com.fplke.msauthentication.dto.request.CreateUserDto;
import com.fplke.msauthentication.dto.request.LoginDto;
import com.fplke.msauthentication.dto.response.TokenDto;
import com.fplke.msauthentication.models.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    User createUser(CreateUserDto createUserDto);

    User getUserDetails(Authentication authentication);

    TokenDto verifyCredentials(LoginDto loginDto);

}
