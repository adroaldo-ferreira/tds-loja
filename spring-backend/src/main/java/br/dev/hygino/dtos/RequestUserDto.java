package br.dev.hygino.dtos;

import br.dev.hygino.entities.AccessLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestUserDto(
        @NotBlank
        @Size(max = 50)
        String username,

        @NotBlank
        @Size(max = 255)
        String password,

        @NotBlank
        @Size(max = 120)
        String fullName,

        @Enumerated(EnumType.STRING)
        AccessLevel accessLevel,

        boolean isActive
) {
}
