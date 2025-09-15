package com.tfg.service;

import com.tfg.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteService   {
    public List<Paciente> findPacienteAll();
    public Paciente findPacienteById(Long id);
    public Paciente createPaciente(Paciente paciente);
    public Paciente updatePaciente(Paciente paciente);
    public void deletePacienteById(Long id);
    public Optional<Paciente> getPaciente(Long id);
}
