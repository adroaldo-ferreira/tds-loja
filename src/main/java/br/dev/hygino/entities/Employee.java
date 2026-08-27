package br.dev.hygino.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_employee")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 120)
    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @NotBlank
    @CPF
    @Size(min = 11, max = 14)
    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Size(max = 60)
    @Column(name = "position", length = 60)
    private String position;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phoneNumber;

    @Email
    @Size(max = 120)
    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "admission_date")
    private LocalDate admissionDate;

    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
