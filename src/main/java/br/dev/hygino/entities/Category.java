package br.dev.hygino.entities;

import br.dev.hygino.dtos.ResponseCategoryDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 80)
    private String name;

    @NotBlank
    @Size(min = 3, max = 200)
    private String description;

    public ResponseCategoryDto toResponseCategoryDto() {
        return new ResponseCategoryDto(id, name, description);
    }
}
