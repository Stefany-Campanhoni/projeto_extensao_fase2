package com.stefanycampanhoni.projeto_extensao_fase2.mentor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record MentorDto(
        @NotBlank
        @Length(max = 45)
        String name,

        @NotBlank
        @Length(max = 200)
        String description,

        @Email
        @NotBlank
        @Length(max = 80)
        String email,

        String password,

        @NotNull
        Integer cityId,

        @NotNull
        Integer specialtyId
) {
}
