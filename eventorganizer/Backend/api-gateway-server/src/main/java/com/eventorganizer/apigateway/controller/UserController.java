package com.eventorganizer.apigateway.controller;

import com.eventorganizer.apigateway.model.LoginResponse;
import com.eventorganizer.apigateway.model.User;
import com.eventorganizer.apigateway.service.JwtService;
import com.eventorganizer.apigateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController
{

    @Autowired
    private UserService service;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @PostMapping("/api/register")
    public User register(@RequestBody User user)
    {
        return service.saveUser(user);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Authenticate the user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                // Fetch the user from the database
                User dbUser = service.findByUserName(user.getUsername());
                if (dbUser == null) {
                    throw new UsernameNotFoundException("User not found");
                }
                // Check if the role matches
                if (!dbUser.getRole().equals(user.getRole())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Role mismatch");
                }

                // Generate and return the JWT token with the role included
                String token = jwtService.generateTokenWithRole(user.getUsername(), dbUser.getRole());

                LoginResponse loginResponse = new LoginResponse(token);

                return ResponseEntity.ok().body(loginResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/api/users/getUserIdByUsername/{username}")
    public int getIdByUsername(@RequestParam String username)
    {
        return service.getIdByUsername(username);
    }


}
