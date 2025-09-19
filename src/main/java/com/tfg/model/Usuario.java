package com.tfg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "usuario",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_public_id", columnNames = "public_id"),
                @UniqueConstraint(name = "uk_usuario_nif", columnNames = "nif"),
                @UniqueConstraint(name = "uk_usuario_email", columnNames = "email")
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING, length = 20)
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode(callSuper=false)
public abstract class Usuario {

    // PK técnica: generada por secuencia
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
    private Long id;

    // ID público del diagrama (UUID)
    @Column(name = "public_id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID publicId = UUID.randomUUID();

    @NotBlank @Size(max = 80)
    private String nombre;

    @NotBlank @Size(max = 120)
    private String apellidos;

    @Past
    private LocalDate fechaNacimiento;

    @Size(max = 20)
    private String telefono;

    @Email @NotBlank @Size(max = 150)
    private String email;

    @NotBlank @Size(min = 8, max = 16)
    private String nif;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoCuenta estadoCuenta = EstadoCuenta.ACTIVO;

    private LocalDateTime ultimoAcceso;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime actualizadoEn;

    @Version
    private Long version;
}
