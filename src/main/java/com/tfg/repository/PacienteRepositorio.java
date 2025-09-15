package com.tfg.repository;

import com.tfg.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {

}
