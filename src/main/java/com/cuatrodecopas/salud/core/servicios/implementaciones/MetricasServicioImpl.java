package com.cuatrodecopas.salud.core.servicios.implementaciones;

import com.cuatrodecopas.salud.core.entidades.Prestacion;
import com.cuatrodecopas.salud.core.servicios.interfaces.MetricasServicio;
import com.cuatrodecopas.salud.data.repositorios.PrestacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricasServicioImpl implements MetricasServicio {
    @Autowired
    PrestacionRepositorio prestacionRepositorio;
    @Override
    public List<Prestacion> obtenerMetricaSegunHabilidadTiempoDeUnProfesional(Long idProfesional, Long idHabilidad, Integer mes) {
        return prestacionRepositorio.findPrestacionesByFechaAndHabilidadAndProfesional(idProfesional,idHabilidad,mes);
    }

    @Override
    public List<Prestacion> findByProfesionalYHabilidadOrdenadoPorPuntajeDeProfesional(Long idProfesional, Long idHabilidad) {
        return prestacionRepositorio.findByProfesionalYHabilidadOrdenadoPorPuntajeDeProfesional(idProfesional,idHabilidad);
    }

}
