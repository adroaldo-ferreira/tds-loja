package br.dev.hygino.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.dev.hygino.dtos.ResponseProductDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 3, max = 150)
  private String name;

  @NotBlank
  @Size(min = 3, max = 40)
  @Column(unique = true)
  private String code;

  @NotBlank
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id")
  private Category category;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "supplier_id")
  private Supplier supplier;

  @NotBlank
  @Size(max = 10)
  private String size;

  @NotBlank
  @Size(min = 3, max = 40)
  private String color;

  @NotNull
  @Positive
  private BigDecimal purchasePrice;

  @NotNull
  @Positive
  private BigDecimal salesPrice;

  @Positive
  private int minimalStock;

  @PositiveOrZero
  private int stock;

  private boolean active;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @PrePersist
  void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public ResponseProductDto toResponseProduct() {
    return new ResponseProductDto(
        id,
        name,
        code,
        description,
        size,
        color,
        purchasePrice,
        salesPrice,
        minimalStock,
        stock,
        category.getName(),
        supplier.getName(),
        active,
        createdAt,
        updatedAt);
  }
}
