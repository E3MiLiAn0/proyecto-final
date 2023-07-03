package com.cuatrodecopas.salud.core.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "calificacion")
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "prestacion_id")
    @JsonIgnore
    private Prestacion prestacion;

    @Column(name = "puntaje_de_profesional_a_usuario")
    private Integer puntajeDeProfesionalAUsuario=0;
    @Column(name = "cometario_profesional_a_usuario")
    private String  cometarioProfesionalAUsuario="sin comentarios";

    @Column(name = "puntaje_de_profesional_a_prestacion")
    private Integer puntajeDeProfesionalAPrestacion=0;
    @Column(name = "cometario_profesional_a_prestacion")
    private String  cometarioProfesionalAPrestacion="sin comentarios";

    @Column(name = "puntaje_de_usuario_a_profesional")
    private Integer puntajeDeUsuarioAProfesional=0;
    @Column(name = "cometario_usuario_a_profesional")
    private String  cometarioUsuarioAProfesional="sin comentarios";

    @Column(name = "puntaje_de_usuario_a_prestacion")
    private Integer puntajeDeUsuariolAPrestacion=0;
    @Column(name = "cometario_usuario_a_prestacion")
    private String  cometarioUsuariolAPrestacion="sin comentarios";
}
