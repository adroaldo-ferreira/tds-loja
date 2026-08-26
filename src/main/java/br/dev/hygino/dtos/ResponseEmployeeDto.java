package br.dev.hygino.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.dev.hygino.entities.Employee;

public record ResponseEmployeeDto(
        Long id,
        String name,
        String cpf,
        String position,
        String phoneNumber,
        String email,
        LocalDate admissionDate,
        BigDecimal salary,
        Boolean active,
        String username,
        LocalDateTime createdAt) {

    public ResponseEmployeeDto(Employee entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getPosition(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getAdmissionDate(),
                entity.getSalary(),
                entity.isActive(),
                entity.getUser() != null ? entity.getUser().getUsername() : null,
                entity.getCreatedAt());
    }
}