package com.tfg.repository;

import com.tfg.model.Grupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    // Buscar por descripción exacta
    Optional<Grupo> findByDescripcion(String descripcion);

    // Buscar por descripción parcial (insensible a mayúsculas/minúsculas)
    Page<Grupo> findByDescripcionContainingIgnoreCase(String descripcion, Pageable pageable);

    // Buscar por capacidad exacta
    Page<Grupo> findByCapacidad(Integer capacidad, Pageable pageable);

    // Buscar todos los grupos con capacidad mayor o igual que X
    Page<Grupo> findByCapacidadGreaterThanEqual(Integer capacidad, Pageable pageable);

    // Saber si ya existe un grupo con esa descripción
    boolean existsByDescripcion(String descripcion);
}

