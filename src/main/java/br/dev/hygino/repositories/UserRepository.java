package br.dev.hygino.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.hygino.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT obj
            FROM User obj
            WHERE UPPER(obj.username) = UPPER(:username)
            """)
    Optional<User> findByUsername(String username);
}
