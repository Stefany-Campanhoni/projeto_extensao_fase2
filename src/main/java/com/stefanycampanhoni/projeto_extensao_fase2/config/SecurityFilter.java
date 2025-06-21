package com.stefanycampanhoni.projeto_extensao_fase2.config;



import com.stefanycampanhoni.projeto_extensao_fase2.jwt.TokenService;
import com.stefanycampanhoni.projeto_extensao_fase2.mentor.Mentor;
import com.stefanycampanhoni.projeto_extensao_fase2.mentor.MentorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = this.recuperarToken(request);

        if (token != null) {
            String login = tokenService.validarToken(token);

            if (login != null) {
                Optional<Mentor> usuarioResult = mentorRepository.findByEmail(login);

                if (usuarioResult.isPresent()) {
                    Mentor mentor = usuarioResult.get();
                    List<GrantedAuthority> authorities = List.of(
                            new SimpleGrantedAuthority("ROLE_" + mentor.getRole().name()) // role vem do seu enum
                    );
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(mentor, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                     throw new RuntimeException("Usuário não encontrado!");
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            return null;
        }
        return token.replace("Bearer ", "");
    }
}


