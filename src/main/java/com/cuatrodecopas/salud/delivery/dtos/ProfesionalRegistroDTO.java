package com.cuatrodecopas.salud.delivery.dtos;

import com.cuatrodecopas.salud.core.entidades.Direccion;
import com.cuatrodecopas.salud.core.entidades.Habilidad;
import com.cuatrodecopas.salud.core.entidades.Profesional;
import com.cuatrodecopas.salud.core.entidades.Usuario;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfesionalRegistroDTO {

    private String matricula;
    private String descripcionProfesional;
    private String profesion;
    private String juridiccionDeLaMatricula;
    private Date fechaDeLaMatricula;
    private String especialidadDeLaMatricula;
    private Boolean guardiaActiva=false;
    private Boolean disponible=true;
    private Direccion direccionProfesional;
    private String dni;
    private List<Long> listaDeHabilidades;
    public Profesional profesionalRegistroDtoAProfesional(Usuario usuario,List<Habilidad> listaDeHabilidades){

        Direccion direccion = Direccion.builder()
                .latitud(direccionProfesional.getLatitud())
                .longitud(direccionProfesional.getLongitud())
                .calle(direccionProfesional.getCalle())
                .numero(direccionProfesional.getNumero())
                .localidad(direccionProfesional.getLocalidad())
                .build();

        return Profesional.builder()
                .matricula(this.matricula)
                .descripcionProfesional(this.descripcionProfesional)
                .profesion(this.profesion)
                .juridiccionDeLaMatricula(this.juridiccionDeLaMatricula)
                .estadoDeLaMatricula(true)
                .fechaDeLaMatricula(this.fechaDeLaMatricula)
                .especialidadDeLaMatricula(this.especialidadDeLaMatricula)
                .guardiaActiva(this.guardiaActiva)
                .disponible(this.disponible)
                .direccionProfesional(direccion)
                .usuario(usuario)
                .build();
    }
}