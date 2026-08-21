package br.dev.hygino.repositories;

import br.dev.hygino.entitiies.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
