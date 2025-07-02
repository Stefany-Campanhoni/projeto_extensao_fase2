package com.stefanycampanhoni.projeto_extensao_fase2.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.stefanycampanhoni.projeto_extensao_fase2.mentor.Mentor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.security.secret}")
    private String secret;

    @Value("${spring.security.expirationTime}")
    private Long expirationTime;

    private final static String ISSUER = "inova-mentor-api-token";

    public String generateToken(Mentor user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getEmail())
                .withExpiresAt(this.getExpirationDate())
                .sign(algorithm);
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (TokenExpiredException e) {
            throw e;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Instant getExpirationDate() {
        return LocalDateTime.now()
                .plusMinutes(expirationTime)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
