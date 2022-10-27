package com.example.springsecurity.security.boundary;

import com.example.springsecurity.security.dto.AuthRequest;
import com.example.springsecurity.security.dto.AuthResponse;
import com.example.springsecurity.security.jwt.JwtTokenUtil;
import com.example.springsecurity.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRestResource {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(), authRequest.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            System.out.println("user.email: " + user.getEmail());
            System.out.println("user.password: " + user.getPassword());

            String accessToken = jwtTokenUtil.generateAccessToken(user);
            AuthResponse authResponse = new AuthResponse(user.getEmail(), accessToken);
            System.out.println("authResponse.email: " + authResponse.getEmail());
            System.out.println("authResponse.accessToken: " + authResponse.getAccessToken());

            return ResponseEntity.ok(authResponse);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
