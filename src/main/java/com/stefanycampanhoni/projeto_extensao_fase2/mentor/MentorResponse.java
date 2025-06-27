package com.stefanycampanhoni.projeto_extensao_fase2.mentor;


public record MentorResponse(
        Long id,
        String email,
        String token,
        Role role
) {
}
