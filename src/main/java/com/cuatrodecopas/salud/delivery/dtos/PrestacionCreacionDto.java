package com.cuatrodecopas.salud.delivery.dtos;

import com.cuatrodecopas.salud.configuracion.Configuraciones;
import com.cuatrodecopas.salud.core.entidades.*;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrestacionCreacionDto {

    private String dniProfesional;
    private String dniUsuario;
    private String descripcion;
    private String latitud;
    private String longitud;
    private String calle;
    private Integer numero;
    private String localidad;
    private Long idHabilidad;
    private Boolean estadoActiva=false;
    private Boolean pendienteDeRespuesta=true;
    private Boolean prestacionAceptada=false;
    private Integer cantidad;

    public Prestacion prestacionCreacionDtoAPretacion(Usuario usuario, Profesional profesional, Habilidad habilidad , Double precioQueCobraProfesional){
        Direccion direccion = Direccion.builder().latitud(this.latitud)
                            .longitud(this.longitud)
                            .calle(this.calle)
                            .numero(this.numero)
                            .localidad(this.localidad)
                            .build();

        return Prestacion.builder().usuario(usuario)
                            .profesional(profesional)
                            .direccionPrestacion(direccion)
                            .habilidad(habilidad)
                            .descripcion(this.descripcion)
                            .estadoActiva(this.estadoActiva)
                            .pendienteDeRespuesta(this.pendienteDeRespuesta)
                            .prestacionAceptada(this.prestacionAceptada)
                            .fechaCreada(Configuraciones.obtenerFechaActual())
                            .precioPrestacionProfesional(precioQueCobraProfesional)
                            .cantidad(this.cantidad)
                            .precioTotal(this.cantidad*precioQueCobraProfesional)
                            .build();
    }

}
