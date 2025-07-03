package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * JwtUtil: Utility class for JWT operations.
 * - Generates JWTs.
 * - Validates JWTs.
 * - Extracts claims (like username, expiration) from JWTs.
 */
@Component
public class    JwtUtil {

    private String secret="sajkhgftyhasilkcnajknbcjasncjbvjNjksncjksncjkncsajknbcjkabscjkbascjkbcsjkcsjbcjkbascjkbjkascsjcbjascbkcsabkjbaskjcbkcjas";

    private long expiration=86400000;

    /**
     * Extracts the username (subject) from the JWT token.
     * @param token The JWT token.
     * @return The username.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the JWT token.
     * @param token The JWT token.
     * @return The expiration Date.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the JWT token.
     * @param token The JWT token.
     * @param claimsResolver Function to resolve the desired claim from Claims.
     * @param <T> Type of the claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     * @param token The JWT token.
     * @return All claims as a Claims object.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // Set the secret key for parsing
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token is expired.
     * @param token The JWT token.
     * @return True if the token is expired, false otherwise.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates a JWT token for a given UserDetails object.
     * Includes username as subject and user's authorities as a custom claim.
     * @param userDetails The UserDetails object representing the authenticated user.
     * @return The generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Add user roles as a custom claim (comma-separated string)
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("roles", roles);
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Creates the JWT token with specified claims, subject, issue date, and expiration date.
     * @param claims Custom claims to include in the token.
     * @param subject The subject of the token (typically username).
     * @return The compact JWT string.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Set custom claims
                .setSubject(subject) // Set the subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set issue date
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Set expiration date
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Sign with secret key and algorithm
                .compact(); // Build and compact the token
    }

    /**
     * Validates the JWT token.
     * Checks if the username in the token matches the UserDetails username and if the token is not expired.
     * @param token The JWT token.
     * @param userDetails The UserDetails object to validate against.
     * @return True if the token is valid, false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Decodes the base64 encoded secret key to create a SecretKey object for signing/verification.
     * @return SecretKey
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}