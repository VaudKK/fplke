package com.fplke.msauthentication.controller;

import com.fplke.msauthentication.dto.request.CreateUserDto;
import com.fplke.msauthentication.dto.request.LoginDto;
import com.fplke.msauthentication.dto.response.TokenDto;
import com.fplke.msauthentication.models.User;
import com.fplke.msauthentication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserDto createUserDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto loginDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.verifyCredentials(loginDto));
    }

    @GetMapping("/user-details")
    public ResponseEntity<User> userDetails(Authentication authentication){
        return ResponseEntity.ok(userService.getUserDetails(authentication));
    }

}



