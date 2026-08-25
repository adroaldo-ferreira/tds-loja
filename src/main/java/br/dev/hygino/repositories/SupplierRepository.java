package br.dev.hygino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.hygino.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
