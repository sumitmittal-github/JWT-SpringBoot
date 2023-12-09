package com.sumit.service;

import com.sumit.config.JwtService;
import com.sumit.dto.AuthenticationRequest;
import com.sumit.dto.AuthenticationResponse;
import com.sumit.dto.RegisterRequest;
import com.sumit.enums.Role;
import com.sumit.entity.User;
import com.sumit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        String firstname = request.getFirstname();
        String lastname = request.getLastname();
        String email = request.getEmail();
        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());
        User user = new User(firstname, lastname ,email, username, password, Role.USER);
        userRepository.save(user);

        // generate new token
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // if there was no error on above line means user was authenticated (Username and password was correct).
        // Now generate a token and return it to the user
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        // generate new token
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

}