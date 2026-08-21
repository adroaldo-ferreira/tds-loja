package br.dev.hygino.dtos;

import br.dev.hygino.entitiies.AccessLevel;

public record ResponseUserDto(
        Long id,
        String username,
        String password,
        String fullName,
        AccessLevel accessLevel,
        boolean isActive
) {
}
