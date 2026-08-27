package br.dev.hygino.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ResponseProductDto(
                Long id,
                String name,
                String code,
                String description,
                String size,
                String color,
                BigDecimal purchasePrice,
                BigDecimal salesPrice,
                int minimalStock,
                int stock,
                String category,
                String supplier,
                Boolean active,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
