package com.cuatrodecopas.salud.core.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "profesional")
public class Profesional{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "descripcion_profesional")
    private String descripcionProfesional;

    @Column(name = "profesion")
    private String profesion;

    @Column(name = "juridiccion_de_la_matricula")
    private String juridiccionDeLaMatricula;

    @Column(name = "estado_de_la_matricula")
    private Boolean estadoDeLaMatricula;

    @Column(name = "fecha_de_la_matricula")
    private Date fechaDeLaMatricula;

    @Column(name = "especialidad_de_la_matricula")
    private String especialidadDeLaMatricula;

    @Column(name = "guardia_activa",columnDefinition = "boolean DEFAULT false")
    private Boolean guardiaActiva;

    @Column(name = "disponible",columnDefinition = "boolean DEFAULT true")
    private Boolean disponible;

    @OneToOne
    @JoinColumn(name = "direccion_Profesional_id")
    private Direccion direccionProfesional;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
