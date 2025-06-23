package com.stefanycampanhoni.projeto_extensao_fase2.config;

import com.stefanycampanhoni.projeto_extensao_fase2.exception.InvalidEmailException;
import com.stefanycampanhoni.projeto_extensao_fase2.jwt.TokenService;
import com.stefanycampanhoni.projeto_extensao_fase2.mentor.Mentor;
import com.stefanycampanhoni.projeto_extensao_fase2.mentor.MentorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MentorRepository mentorRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String login = tokenService.validateToken(this.getTokenFromHeader(request));

        if (login != null) {
            Mentor mentor = mentorRepository.findByEmail(login).orElseThrow(InvalidEmailException::new);

            List<GrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + mentor.getRole().name())
            );
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(mentor, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            throw new RuntimeException("Token de autenticação não encontrado!");
        }

        return token.replace("Bearer ", "");
    }
}


