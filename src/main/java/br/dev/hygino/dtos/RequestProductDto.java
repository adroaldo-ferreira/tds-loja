package br.dev.hygino.dtos;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public record RequestProductDto(
        @NotBlank @Size(min = 3, max = 150) String name,

        @NotBlank @Size(min = 3, max = 40) String code,

        @NotBlank String description,

        @NotBlank @Size(max = 10) String size,

        @NotBlank @Size(min = 3, max = 40) String color,

        @NotNull @Positive BigDecimal purchasePrice,

        @NotNull @Positive BigDecimal salesPrice,

        @PositiveOrZero int minimalStock,

        @PositiveOrZero int stock,

        @NotNull @Positive Long categoryId,

        @NotNull @Positive Long supplierId,

        Boolean active) {

    public RequestProductDto {
        if (active == null) {
            active = true;
        }
    }
}