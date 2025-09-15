package com.tfg.controller;
import com.tfg.exception.ModeloNotFoundException;
import com.tfg.model.Paciente;
import com.tfg.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService service;
    @GetMapping
    public ResponseEntity<List<Paciente>> getAllPacientes(){
        List<Paciente> pacientes = new ArrayList<>();
        pacientes = service.findPacienteAll();
        return new ResponseEntity<List<Paciente>>(pacientes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Paciente> createPaciente(@Valid @RequestBody Paciente paciente){
        service.createPaciente(paciente);
        return new ResponseEntity<>(paciente, HttpStatus.CREATED);
    }

    @GetMapping("/id")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable Long id){
        Paciente paciente = service.getPaciente(id).orElseThrow( () -> new ModeloNotFoundException( "Paciente no encontrado" ));
        return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<Paciente> deletePaciente(@PathVariable("id") Long id){
        service.deletePacienteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/id")
    public Paciente updatePaciente(@PathVariable("id") Long id, @Valid @RequestBody Paciente paciente){
        Paciente dbpaciente= service.getPaciente(id).orElseThrow( () -> new ModeloNotFoundException( "Paciente no encontrado" ));
        dbpaciente.setNombre(paciente.getNombre());
        dbpaciente.setApellidos(paciente.getApellidos());
        dbpaciente.setCiudad(paciente.getCiudad());
        dbpaciente.setDireccion(paciente.getDireccion());
        dbpaciente.setPais(paciente.getPais());
        return service.updatePaciente(dbpaciente);
    }

}
