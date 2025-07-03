package com.example.demo.model;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * User: JPA Entity representing a user in the database.
 * Implements Spring Security's UserDetails interface to integrate with authentication.
 */

@Entity
@Table(name = "user") // Maps to 'users' table in MySQL
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing primary key
    private Long id;

    @Column(unique = true, nullable = false) // Username must be unique and not null
    private String username;

    @Column(nullable = false) // Password must not be null
    private String password;

    @Column(nullable = false)
    private String roles; // Stores roles as a comma-separated string (e.g., "ROLE_USER,ROLE_ADMIN")

    /**
     * Returns the authorities (roles) granted to the user.
     * Converts the comma-separated roles string into a collection of GrantedAuthority objects.
     * @return Collection of GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // The following methods are part of UserDetails interface.
    // For simplicity, they return true, but in a real application,
    // you might implement logic for account expiration, locking, etc.

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public User() {
    }

    public User(Long id, String username, String password, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}