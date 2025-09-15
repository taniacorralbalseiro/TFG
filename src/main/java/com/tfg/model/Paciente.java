package com.tfg.model;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@EqualsAndHashCode(callSuper=false) //lombok->genera automaticamente equals y hashcode
//@Builder // lombok->implementa patron builder
@AllArgsConstructor // lombok->genera constructor con todos los atributos de la clase
@NoArgsConstructor  // lombok->genera constructor vacío
@Entity
@Data   // lombok->genera getters y setters, tostring, ...
@Table(name="TBL_Paciente")
public class Paciente {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPaciente")
    @SequenceGenerator(name = "seqPaciente", allocationSize = 1, sequenceName = "SEQ_PACIENTE")
    private Long id;
    //@Builder.Default
    @NotNull@NotBlank
    private String nombre;
    @NotNull @NotBlank
    String apellidos;
    @NotBlank(message = "Direccion es requerida")
    @Size(min = 5, max = 100)
    String direccion;
    @NotNull @NotBlank
    String ciudad;
    @NotNull @NotBlank
    String pais;
}
