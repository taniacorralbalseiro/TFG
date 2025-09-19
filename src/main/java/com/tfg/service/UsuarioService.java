package com.tfg.service;

import com.tfg.model.Paciente;
import com.tfg.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
public interface UsuarioService {
    Page<Usuario> findAll(Pageable pageable);
    Usuario getByPublicId(UUID publicId);
    Usuario getByNif(String nif);

    // Actualiza campos comunes de Usuario (nombre, apellidos, email, telefono, fechaNacimiento, estadoCuenta)
    Usuario update(UUID publicId, Usuario cambios);

    // Desactiva o elimina según tu política. Aquí hard-delete:
    void delete(UUID publicId);
}
