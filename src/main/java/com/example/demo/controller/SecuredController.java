package com.example.demo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SecuredController: Provides example endpoints protected by JWT authentication and role-based authorization.
 * - /api/secured/user: Accessible by users with 'ROLE_USER' or 'ROLE_ADMIN'.
 * - /api/secured/admin: Accessible only by users with 'ROLE_ADMIN'.
 */
@RestController
@RequestMapping("/api/secured")
public class SecuredController {

    /**
     * Example endpoint accessible by regular users and admins.
     * Requires 'ROLE_USER' or 'ROLE_ADMIN'.
     * @return A message for authenticated users.
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userEndpoint() {
        return "Hello from User Secured Endpoint! You are authenticated and authorized as a USER or ADMIN.";
    }

    /**
     * Example endpoint accessible only by administrators.
     * Requires 'ROLE_ADMIN'.
     * @return A message for authenticated administrators.
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "Hello from Admin Secured Endpoint! You are authenticated and authorized as an ADMIN.";
    }
}