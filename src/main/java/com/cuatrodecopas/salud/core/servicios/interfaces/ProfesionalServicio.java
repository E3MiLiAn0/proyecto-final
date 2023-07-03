package com.cuatrodecopas.salud.core.servicios.interfaces;

import com.cuatrodecopas.salud.core.entidades.Profesional;
import com.cuatrodecopas.salud.core.entidades.ProfesionalHabilidad;

import java.util.List;

public interface ProfesionalServicio {
    List<Profesional> filtrarProfesionalesPorLocalidadYHabilidad(String localidad, Long idHabilidad) throws Exception;


    void actualizarGuardia(Profesional profesional) throws Exception;
    List<Profesional> obtenerListProfesionalesConGuardiaActivaYDisponible();
    Profesional obtenerProfesionalPorDni(String dni);

    List<Profesional> obtenerListProfesionalesConGuardiaActivaYDisponiblePorLocalidad(String localidad);

    void registrarProfesional(Profesional profesional) throws Exception;

    Profesional obtenerProfesionalPorId(Long idProfesional);

    List<ProfesionalHabilidad> obtenerTodasLasHabilidadesDeUnProfesionalPorId(Long idProfesional) throws Exception;

    Profesional validarProfesional(Profesional profesional) throws Exception;

    String validarRegistroProfesional(String matricula,String dniProfesional);


}
