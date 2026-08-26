package br.dev.hygino.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestSupplierDto(
                Long userId,
                @NotBlank @Size(min = 3, max = 150) String name,
                @Size(max = 18) String cnpj,
                @Size(max = 120) String contactName,
                @Size(max = 20) String phoneNumber,
                @Email @Size(max = 120) String email,
                @Size(max = 200) String address,
                @Size(max = 80) String city,
                @Size(min = 2, max = 2) String state) {
}