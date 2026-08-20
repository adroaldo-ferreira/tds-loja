package br.dev.hygino.entitiies;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Category {

    @Id
    private Long id;

    @NotBlank
    @Size(min = 3, max = 200)
    private String description;

    @NotBlank
    @Size(min = 3, max = 80)
    private String name;
}
