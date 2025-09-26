package com.tfg.service;

import com.tfg.model.Centro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CentroService {

    Centro create(Centro centro);

    Centro update(Long id, Centro cambios);

    Centro getById(Long id);

    void delete(Long id);

    Page<Centro> list(Pageable pageable);

    boolean existsEmail(String email);

    Centro getByEmail(String email);

    Page<Centro> findByCiudad(String ciudad, Pageable pageable);

    Page<Centro> findByProvincia(String provincia, Pageable pageable);

    Page<Centro> findByCiudadAndProvincia(String ciudad, String provincia, Pageable pageable);

    Page<Centro> searchByNombre(String parcialNombre, Pageable pageable);
}
