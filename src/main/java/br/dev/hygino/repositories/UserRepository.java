package br.dev.hygino.repositories;

import br.dev.hygino.entitiies.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
