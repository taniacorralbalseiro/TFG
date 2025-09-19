package com.tfg.service;

import com.tfg.model.Empleado;
import com.tfg.model.RolEmpleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EmpleadoService {
    Empleado create(Empleado nuevo);
    Empleado getByPublicId(UUID publicId);
    Empleado getByNif(String nif);
    Empleado update(UUID publicId, Empleado cambios);
    void delete(UUID publicId);
    Page<Empleado> findAll(Pageable pageable);
    Page<Empleado> listByCentro(UUID centroId, Pageable pageable);
    Page<Empleado> listByRol(RolEmpleado rol, Pageable pageable);
}

