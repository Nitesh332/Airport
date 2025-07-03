package com.example.demo.controller;


import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CustomPasswordDetailsService;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.util.JwtUtil;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController: Handles user authentication (login) and registration.
 * - /api/auth/login: Authenticates user and returns a JWT.
 * - /api/auth/register: Registers a new user.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Handles user login and generates a JWT.
     * @param authRequest Contains username and password.
     * @return AuthResponse with the JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()&&userRepository.findByPassword(authRequest.getPassword()).isPresent()) {
            // If authenticated, load UserDetails and generate JWT
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            // If authentication fails, return unauthorized status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Registers a new user.
     * @param authRequest Contains username and password for the new user.
     * @return ResponseEntity indicating success or failure.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest authRequest) {
        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()) {
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("response","Username is already taken!");
            return ResponseEntity.badRequest().body(String.valueOf(jsonObject));
        }

        // Create a new user
        User newUser = new User();
        newUser.setUsername(authRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(authRequest.getPassword())); // Encode password
        newUser.setRoles("ROLE_USER"); // Default role for new users

        userRepository.save(newUser);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("response","User registered successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(jsonObject));
    }
}