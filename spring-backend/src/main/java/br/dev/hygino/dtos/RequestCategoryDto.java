package br.dev.hygino.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestCategoryDto(
        @NotBlank
        @Size(min = 3, max = 80)
        String name,

        @NotBlank
        @Size(min = 3, max = 200)
        String description
) {
}
