package com.pccompare.gestionusuarios.repository;

import com.pccompare.gestionusuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Acceso a datos de {@link Usuario}. Spring Data JPA genera la
 * implementación en tiempo de ejecución a partir de la firma de los métodos.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsername(String username);

    /** Usado en el login: el usuario puede identificarse con su email o su username. */
    Optional<Usuario> findByEmailOrUsername(String email, String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
