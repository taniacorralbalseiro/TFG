package com.tfg.repository;

import com.tfg.model.Empleado;
import com.tfg.model.RolEmpleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmpleadoRepositorio extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByPublicId(UUID publicId);
    Optional<Empleado> findByNif(String nif);
    Page<Empleado> findByCentroId(UUID centroId, Pageable pageable);
    Page<Empleado> findByRol(RolEmpleado rol, Pageable pageable);
}
