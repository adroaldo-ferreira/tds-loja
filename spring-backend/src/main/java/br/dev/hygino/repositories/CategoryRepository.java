package br.dev.hygino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.hygino.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
