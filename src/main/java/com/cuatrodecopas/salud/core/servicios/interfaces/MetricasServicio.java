package com.cuatrodecopas.salud.core.servicios.interfaces;

import com.cuatrodecopas.salud.core.entidades.Prestacion;

import java.util.List;


public interface MetricasServicio {
    List<Prestacion> obtenerMetricaSegunHabilidadTiempoDeUnProfesional(Long idProfesional,Long idHabilidad,Integer mes);
    List<Prestacion>  findByProfesionalYHabilidadOrdenadoPorPuntajeDeProfesional(Long idProfesional,Long idHabilidad);

}
