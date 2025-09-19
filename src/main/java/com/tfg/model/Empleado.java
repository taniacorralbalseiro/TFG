package com.tfg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "empleado",
        indexes = {
                @Index(name = "ix_empleado_centro", columnList = "centro_id")
        }
)
@DiscriminatorValue("EMPLEADO")
@Getter @Setter @NoArgsConstructor
public class Empleado extends Usuario {

    // Campos del diagrama
    @Size(max = 40)
    @Column(name = "n_colegiado")
    private String nColegiado;

    @Size(max = 80)
    private String especialidad;

    private LocalDate fechaContratacion;
    private LocalDate fechaCese;

    private Double salario;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RolEmpleado rol;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 15)
    private EstadoEmpleado estado; // "testado" del diagrama interpretado como estado del empleado

    // FK a Centro como UUID (según tu diagrama)
    @Column(name = "centro_id", columnDefinition = "uuid")
    private UUID centroId;
}