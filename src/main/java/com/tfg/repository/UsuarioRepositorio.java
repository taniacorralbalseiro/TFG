package com.tfg.repository;
import com.tfg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByPublicId(UUID publicId);
    Optional<Usuario> findByNif(String nif);
    boolean existsByNif(String nif);
    boolean existsByEmail(String email);
}