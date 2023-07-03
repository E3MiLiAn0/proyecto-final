package com.cuatrodecopas.salud.core.servicios.implementaciones;

import com.cuatrodecopas.salud.core.entidades.ProfesionalHabilidad;
import com.cuatrodecopas.salud.core.servicios.interfaces.ProfesionalHabilidadServicio;
import com.cuatrodecopas.salud.data.repositorios.ProfesionalHabilidadRespositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesionalHabilidadServicioImpl implements ProfesionalHabilidadServicio {

    @Autowired
    private ProfesionalHabilidadRespositorio profesionalHabilidadRespositorio;
    @Override
    public List<ProfesionalHabilidad> obtenerTodasLasHabilidadesPorIdDeUnProfesional(Long idProfesional) {
        return profesionalHabilidadRespositorio.listaDeHabilidadesDeUnProfesional(idProfesional);
    }

    @Override
    public Double precioQueCobraProfesionalPorHabilidad(Long idHabilidad, Long idProfesional) {
        return profesionalHabilidadRespositorio.findByProfesionalIdAndHabilidadId(idHabilidad,idProfesional).getPrecioProfesional();
    }
}
