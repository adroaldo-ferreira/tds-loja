package br.dev.hygino.entitiies;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(max = 255)
    private String password;

    @NotBlank
    @Size(max = 120)
    private String fullName;

    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel = AccessLevel.OPERATOR;

    private boolean isActive = true;

    private LocalDateTime lastLogin;

    @NotBlank
    private LocalDateTime createdAt;
}
