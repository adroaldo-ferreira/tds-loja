package br.dev.hygino.repositories;

import br.dev.hygino.entitiies.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
