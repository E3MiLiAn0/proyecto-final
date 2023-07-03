package com.cuatrodecopas.salud.core.servicios.interfaces;

import com.cuatrodecopas.salud.core.entidades.Habilidad;
import com.cuatrodecopas.salud.core.entidades.ProfesionalHabilidad;

import java.util.List;

public interface ProfesionalHabilidadServicio {
    List<ProfesionalHabilidad> obtenerTodasLasHabilidadesPorIdDeUnProfesional(Long idProfesional);

    Double precioQueCobraProfesionalPorHabilidad( Long idProfesional,Long idHabilidad);


}
