package br.dev.hygino.entitiies;

import java.time.LocalDateTime;

import br.dev.hygino.dtos.ResponseUserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
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

    @NotNull
    private LocalDateTime createdAt;

    public ResponseUserDto toResponseDto() {
        return new ResponseUserDto(id, username, password, fullName, accessLevel, isActive, lastLogin);
    }
}
