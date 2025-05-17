package com.example.auth.Controller;

import com.example.auth.model.Users;
import com.example.auth.service.JWTService;
import com.example.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return userService.verifyUser(user);
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/admin/data")
//    public ResponseEntity<String> getAdminData() {
//        return ResponseEntity.ok("Secret admin data");
//    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> whoAmI(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7); // Remove "Bearer "
      //  System.out.println(token);
        String username = jwtService.extractUsername(token);
        String email = jwtService.extractEmail(token);

        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        response.put("email", email);

        return ResponseEntity.ok(response);
    }

//    @PatchMapping("/me/update")

}
