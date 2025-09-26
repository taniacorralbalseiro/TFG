package com.tfg.model;

import com.tfg.model.enumerados.EstadoEmpleado;
import com.tfg.model.enumerados.RolEmpleado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "empleado",
        indexes = {
                @Index(name = "ix_empleado_centro", columnList = "centro_id")
        }
)
@DiscriminatorValue("EMPLEADO")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString
@SuperBuilder
public class Empleado extends Usuario {

    @Size(max = 40)
    @Column(name = "n_colegiado")
    @NotBlank
    private String nColegiado;

    @Column(nullable = false)
    @Size(max = 80)
    @NotBlank
    private String especialidad;

    @Column(nullable = false)
    @NotNull
    private LocalDate fechaContratacion;

    @Column(nullable = true)
    private LocalDate fechaCese;

    @Column(nullable = false)
    @NotNull
    @PositiveOrZero
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    @Column(length = 40, nullable = false)
    @NotNull
    private RolEmpleado rol;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 45, nullable = false)
    @NotNull
    private EstadoEmpleado estado;

    @OneToMany(mappedBy = "empleadoImparte", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Sesion> sesionesImpartidas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "centro_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_empleado_centro")
    )
    @ToString.Exclude
    private Centro centro;



    public void addSesion(Sesion s) {
        this.sesionesImpartidas.add(s);
        s.setEmpleadoImparte(this);
    }
    public void removeSesion(Sesion s) {
        this.sesionesImpartidas.remove(s);
        s.setEmpleadoImparte(null);
    }

}