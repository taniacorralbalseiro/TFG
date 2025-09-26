package com.tfg.service;

import com.tfg.model.Centro;
import com.tfg.repository.CentroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CentroServiceImpl implements CentroService {

    private final CentroRepository centroRepository;

    @Override
    public Centro create(Centro centro) {
        // validación de unicidad a nivel de servicio
        if (centro.getEmail() != null && centroRepository.existsByEmail(centro.getEmail())) {
            throw new DataIntegrityViolationException("Ya existe un centro con email: " + centro.getEmail());
        }
        return centroRepository.save(centro);
    }

    @Override
    public Centro update(Long id, Centro cambios) {
        Centro actual = centroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Centro no encontrado: " + id));

        // Si cambias email, valida unicidad
        if (cambios.getEmail() != null
                && !cambios.getEmail().equalsIgnoreCase(actual.getEmail())
                && centroRepository.existsByEmail(cambios.getEmail())) {
            throw new DataIntegrityViolationException("Ya existe un centro con email: " + cambios.getEmail());
        }

        actual.setNombre(cambios.getNombre());
        actual.setDireccion(cambios.getDireccion());
        actual.setCiudad(cambios.getCiudad());
        actual.setProvincia(cambios.getProvincia());
        actual.setTelefono(cambios.getTelefono());
        actual.setEmail(cambios.getEmail());

        return centroRepository.save(actual);
    }

    @Override
    @Transactional(readOnly = true)
    public Centro getById(Long id) {
        return centroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Centro no encontrado: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!centroRepository.existsById(id)) {
            throw new EntityNotFoundException("Centro no encontrado: " + id);
        }
        centroRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Centro> list(Pageable pageable) {
        return centroRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsEmail(String email) {
        return centroRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Centro getByEmail(String email) {
        return centroRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No existe centro con email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Centro> findByCiudad(String ciudad, Pageable pageable) {
        return centroRepository.findByCiudadIgnoreCase(ciudad, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Centro> findByProvincia(String provincia, Pageable pageable) {
        return centroRepository.findByProvinciaIgnoreCase(provincia, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Centro> findByCiudadAndProvincia(String ciudad, String provincia, Pageable pageable) {
        return centroRepository.findByCiudadIgnoreCaseAndProvinciaIgnoreCase(ciudad, provincia, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Centro> searchByNombre(String parcialNombre, Pageable pageable) {
        return centroRepository.findByNombreContainingIgnoreCase(parcialNombre, pageable);
    }
}

