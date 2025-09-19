package com.tfg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "paciente",
        indexes = {
                @Index(name = "ix_paciente_centro", columnList = "centro_id"),
                @Index(name = "ix_paciente_grupo", columnList = "grupo_id")
        }
)
@DiscriminatorValue("PACIENTE")
@Getter @Setter @NoArgsConstructor
public class Paciente extends Usuario {

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private NivelCognitivo nivelCognitivo; // del diagrama

    @Size(max = 1000)
    private String alergias; // ?

    // Claves foráneas como UUID (según tu diagrama). Se pueden convertir a @ManyToOne cuando definas las entidades.
    @Column(name = "centro_id", columnDefinition = "uuid")
    private UUID centroId;

    @Column(name = "grupo_id", columnDefinition = "uuid")
    private UUID grupoId;
}

