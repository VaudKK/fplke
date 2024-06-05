package com.fplke.msauthentication.service;

import com.fplke.msauthentication.dto.request.CreateUserDto;
import com.fplke.msauthentication.dto.response.TokenDto;
import com.fplke.msauthentication.models.User;

public interface UserService {

    User createUser(CreateUserDto createUserDto);

    User findUserByEmail(String email);

    TokenDto verifyCredentials(String email, String password);

}
