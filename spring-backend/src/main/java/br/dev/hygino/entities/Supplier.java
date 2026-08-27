package br.dev.hygino.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import br.dev.hygino.dtos.ResponseSupplierDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "tb_supplier")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 150)
    @Column(name = "company_name", nullable = false, length = 150)
    private String name;

    @Size(max = 18)
    @Column(name = "cnpj", unique = true, length = 18)
    private String cnpj;

    @Size(max = 120)
    @Column(name = "contact_name", length = 120)
    private String contactName;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phoneNumber;

    @Email
    @Size(max = 120)
    @Column(name = "email", length = 120)
    private String email;

    @Size(max = 200)
    @Column(name = "address", length = 200)
    private String address;

    @Size(max = 80)
    @Column(name = "city", length = 80)
    private String city;

    @Size(min = 2, max = 2)
    @Column(name = "state", length = 2)
    private String state;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany
    private final List<Product> products = new ArrayList<>();

    public ResponseSupplierDto toResponseSupplier() {
        return new ResponseSupplierDto(
                id,
                name,
                cnpj,
                contactName,
                phoneNumber,
                email,
                address,
                city,
                state,
                active,
                createdAt);
    }
}