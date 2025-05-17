package com.example.auth.service;

import com.example.auth.dto.UpdateUserRequest;
import com.example.auth.model.Users;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public String verifyUser(Users user){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername(), user.getEmail());
        return "Failed";
    }

    public Users updateProfile(String authHeader, UpdateUserRequest request){
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);
        Users user = userRepository.findByEmail(email);
        if(user == null)
            throw new RuntimeException("User not found!!");
        if(request.getFirstName() != null){
            user.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null){
            user.setLastName(request.getLastName());
        }
        if(request.getPassword() != null){
            user.setPassword(encoder.encode(request.getPassword()));
        }
        return userRepository.save(user);
    }
}
