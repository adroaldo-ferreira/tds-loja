package br.dev.hygino.dtos;

import java.time.LocalDateTime;

public record ResponseSupplierDto(
        Long id,
        String name,
        String cnpj,
        String contactName,
        String phoneNumber,
        String email,
        String address,
        String city,
        String state,
        Boolean active,
        LocalDateTime createdAt) {
}
