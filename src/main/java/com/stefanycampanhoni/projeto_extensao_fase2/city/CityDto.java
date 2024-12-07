package com.stefanycampanhoni.projeto_extensao_fase2.city;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CityDto(
        @NotNull
        @NotBlank
        @Length(max = 45)
        String name,

        @NotNull
        @NotBlank
        @Length(max = 45)
        String state
) {
}
