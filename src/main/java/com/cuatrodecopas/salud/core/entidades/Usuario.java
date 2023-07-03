package com.cuatrodecopas.salud.core.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Column(name = "imagen_dni_frente")
    private String imagenDniFrente;
    @NotBlank
    @Column(name = "imagen_dni_reverso")
    private String imagenDniReverso;
    @NotBlank
    @Column(name = "imagen_soteniendo_dni")
    private String imagenSosteniendoDni;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Email
    @Column(name = "mail",unique = true, nullable = false)
    private String mail;

    @NotBlank
    @Column(name = "dni", unique = true, nullable = false)
    private String dni;

    @Size(min = 4)
    @Column(name = "contrasenia")
    private String password;

    @Column(name = "descripcion")
    private String descripcionPersonal;

    @Column(name = "valoracionTotal")
    private Double valoracionTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;

    @Column(name = "cuenta_no_bloqueada")
    private Boolean cuentaNoBloqueada;

    @Column(name = "es_profesional")
    private Boolean esProfesional;



}
