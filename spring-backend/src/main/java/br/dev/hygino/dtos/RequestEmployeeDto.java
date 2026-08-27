package br.dev.hygino.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

public record RequestEmployeeDto(

        @NotBlank(message = "Name is required") @Size(min = 3, max = 120) String name,

        @NotBlank(message = "CPF is required") @CPF(message = "Invalid CPF") @Size(min = 11, max = 14) String cpf,

        @Size(max = 60) String position,

        @Size(max = 20) String phoneNumber,

        @Email(message = "Invalid email") @Size(max = 120) String email,

        LocalDate admissionDate,

        @PositiveOrZero @Digits(integer = 10, fraction = 2) BigDecimal salary,

        Long userId,
        Boolean isActive) {
}