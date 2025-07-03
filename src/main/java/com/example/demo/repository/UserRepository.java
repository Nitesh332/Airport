package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    /**
     * Finds a User by their username.
     * Spring Data JPA automatically generates the query based on the method name.
     * @param username The username to search for.
     * @return An Optional containing the User if found, or empty otherwise.
     */
    Optional<User> findByUsername(String username);
    Optional<User> findByPassword(String password);
}