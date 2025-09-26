package com.tfg.repository;

import com.tfg.model.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByPublicId(UUID publicId);
    Optional<Paciente> findByNif(String nif);
    Page<Paciente> findByCentroId(UUID centroId, Pageable pageable);
}
