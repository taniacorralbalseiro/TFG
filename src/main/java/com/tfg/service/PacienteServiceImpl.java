package com.tfg.service;

import com.tfg.model.Paciente;
import com.tfg.repository.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {
    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Override
    public Paciente findPacienteById(Long id) {
        return pacienteRepositorio.findById(id).get();
    }

    @Override
    public List<Paciente> findPacienteAll() {
        return pacienteRepositorio.findAll();
    }

    @Override
    public void deletePacienteById(Long id) {
        pacienteRepositorio.deleteById(id);
    }

    @Override
    public Paciente createPaciente(Paciente paciente) {
        return pacienteRepositorio.saveAndFlush(paciente);
    }

    @Override
    public Paciente updatePaciente(Paciente paciente) {
        return pacienteRepositorio.save(paciente);
    }

    @Override
    public Optional<Paciente> getPaciente(Long id) {
        return pacienteRepositorio.findById(id);
    }
}
