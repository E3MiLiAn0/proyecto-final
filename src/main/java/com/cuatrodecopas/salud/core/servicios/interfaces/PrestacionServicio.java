package com.cuatrodecopas.salud.core.servicios.interfaces;

import com.cuatrodecopas.salud.core.entidades.Prestacion;

import java.util.List;

public interface PrestacionServicio {
    List<Prestacion>listaDeSolicitudesDePrestacionesDeUnProfesionalPendientes(String dni);
    void confirmarPrestacion(Long idPrestacion, Boolean confirmacion) throws Exception;
    void crearPrestacion(Prestacion prestacion);

    Prestacion obtenerUltimaPrestacionSolicitadaPorElUsuario(String dni);

    Prestacion obtenerPrestacionActivaPorDniDelUsuario(String dni);

    List<Prestacion> obtenerListaDePrestacionesPorIdProfesional(Long id);

    List<Prestacion> obtenerListaDePrestacionesPorIdUsuario(Long id);

    Prestacion obtenerPrestacionPorId(Long id) throws Exception;

    void finalizarPrestacionActiva(Long id);

    Prestacion obtenerPrestacionActivaPorDniDelProfesional(String dniProfesional);

    void pagarPrestacion(Long id);

    void cancelarUnaPrestacionPendienteDeUnProfesional(Long idPrestacion);

    List<Prestacion> obtenerPrestacionesFinalizadasPorUnProfesional(Long idProfesional) throws Exception;

    List<Prestacion> obtenerPrestacionesFinalizadasPorUnUsuario(Long idUsuario) throws Exception;

    Integer cantidadDeVecesRealizoUnaHabilidad(Long idProfesional, Long idHabilidad );
}
