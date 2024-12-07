package com.stefanycampanhoni.projeto_extensao_fase2.specialty;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record SpecialtyDto(
        @NotBlank
        @Length(max = 50)
        String name,

        @NotBlank
        @Length(max = 50)
        String type
) {
}
