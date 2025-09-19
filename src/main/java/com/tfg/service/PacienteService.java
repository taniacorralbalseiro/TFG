package com.tfg.service;

import com.tfg.model.Paciente;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PacienteService {
    Paciente create(Paciente nuevo);                 // crea Paciente (con Usuario base)
    Paciente getByPublicId(UUID publicId);
    Paciente getByNif(String nif);
    Paciente update(UUID publicId, Paciente cambios);
    void delete(UUID publicId);
    Page<Paciente> findAll(Pageable pageable);
    Page<Paciente> listByCentro(UUID centroId, Pageable pageable);
}
