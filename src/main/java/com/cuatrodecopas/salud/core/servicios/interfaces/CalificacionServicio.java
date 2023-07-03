package com.cuatrodecopas.salud.core.servicios.interfaces;

import com.cuatrodecopas.salud.core.entidades.Calificacion;
import com.cuatrodecopas.salud.core.entidades.Prestacion;

public interface CalificacionServicio {
    Calificacion obtenercalificacionPorId(Long idCalificacion);

    Double obtenerCalificacionPromedioDeUnProfesional(Long idProfesional);

    Double obtenerCalificacionPromedioDeUnUsuario(Long idUsuario);

    void crearCalificacion(Prestacion prestacionEncontrada);

    void profesionalCalificaUsuario(Long idPrestacion, String comentario, Integer puntaje) throws Exception;

    void usuariolCalificaProfesiona(Long idPrestacion, String comentario, Integer puntaje) throws Exception;

   /* void usuarioCalificaProfesional(Calificacion calificacion) throws Exception;
    void usuarioCalificaPrestacion(Calificacion calificacion);

    void profesionalCalificaUsuario(Calificacion calificacion);
    void profesionalCalificaPrestacion(Calificacion calificacion);

    void guardarCalificacion(Calificacion calificacion);*/
}
