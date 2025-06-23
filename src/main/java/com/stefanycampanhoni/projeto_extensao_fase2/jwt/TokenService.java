package com.stefanycampanhoni.projeto_extensao_fase2.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.stefanycampanhoni.projeto_extensao_fase2.mentor.Mentor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {
    @org.springframework.beans.factory.annotation.Value("${spring.security.secret}")
    private String secret;
    @Value("${spring.security.expirationTime}")
    private Long expirationTime;

    public String gerarToken(Mentor usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer("exemplo-api-token")
                .withSubject(usuario.getEmail())
                .withExpiresAt(this.gerarDataValidadeToken())
                .sign(algorithm);

        return token;
    }

    public String validarToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.require(algorithm)
                    .withIssuer("exemplo-api-token")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Instant gerarDataValidadeToken() {
        return LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.of("-03:00"));
    }
}
