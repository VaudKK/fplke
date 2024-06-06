package com.fplke.msauthentication.service.impl;

import com.fplke.msauthentication.client.FplClient;
import com.fplke.msauthentication.dto.request.CreateUserDto;
import com.fplke.msauthentication.dto.request.LoginDto;
import com.fplke.msauthentication.dto.response.TokenDto;
import com.fplke.msauthentication.exceptions.EmailExistsException;
import com.fplke.msauthentication.exceptions.TeamIdExistsException;
import com.fplke.msauthentication.models.User;
import com.fplke.msauthentication.repository.UserRepository;
import com.fplke.msauthentication.service.JwtService;
import com.fplke.msauthentication.service.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Primary
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FplClient fplClient;

    public UserServiceImpl(UserRepository userRepository,
                           JwtService jwtService,
                           BCryptPasswordEncoder bCryptPasswordEncoder, FplClient fplClient) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.fplClient = fplClient;
    }

    @Override
    public User createUser(CreateUserDto createUserDto) {
        if(userRepository.findByEmail(createUserDto.email()).isPresent()){
            throw new EmailExistsException("User exists with specified email");
        }

        if(userRepository.findByTeamId(createUserDto.teamId()).isPresent()){
            throw new TeamIdExistsException("The team id already exists");
        }

        var fplDetails = fplClient.getFplClient(createUserDto.teamId());

        var newUser = new User().buildNewUser(createUserDto,fplDetails);
        newUser.encodePassword(bCryptPasswordEncoder::encode);

        return userRepository.save(newUser) ;
    }

    @Override
    public User findUserByEmail(String email) {
        var user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    public TokenDto verifyCredentials(LoginDto loginDto) {
        var user = userRepository.findByEmail(loginDto.username());
        if(user.isEmpty()){
            throw new BadCredentialsException("Invalid credentials");
        }

        if(!bCryptPasswordEncoder.matches(loginDto.password(), user.get().getPassword())){
            throw new BadCredentialsException("Invalid credentials");
        }

        var token = jwtService.generateToken(Map.of("username",user.get().getUsername()),user.get());
        return new TokenDto(token, jwtService.getExpiresIn()/1000);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }


}
