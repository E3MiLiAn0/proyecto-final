package com.cuatrodecopas.salud.core.servicios.interfaces;

import com.cuatrodecopas.salud.core.entidades.Habilidad;

import java.util.List;

public interface HabilidadServicio {

    List<Habilidad> obtenerListaDeHabilidadesPorIds(List<Long> listadeHabilidadesPorIds) throws Exception;

    Habilidad obtenerHabilidadPorId(Long idHabilidad);
}
