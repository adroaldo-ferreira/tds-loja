package br.dev.hygino.entities;

import java.util.ArrayList;
import java.util.List;

import br.dev.hygino.dtos.ResponseCategoryDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_category")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 80)
    private String name;

    @NotBlank
    @Size(min = 3, max = 200)
    private String description;

    @OneToMany
    private final List<Product> products = new ArrayList<>();

    public ResponseCategoryDto toResponseCategoryDto() {
        return new ResponseCategoryDto(id, name, description);
    }
}
