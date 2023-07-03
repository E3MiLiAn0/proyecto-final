package com.cuatrodecopas.salud.core.servicios.implementaciones;

import com.cuatrodecopas.salud.core.entidades.Habilidad;
import com.cuatrodecopas.salud.core.servicios.interfaces.HabilidadServicio;
import com.cuatrodecopas.salud.data.repositorios.HabilidadRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HabilidadServicioImpl implements HabilidadServicio {

    @Autowired
    private HabilidadRepositorio habilidadRepositorio;
    @Override
    public List<Habilidad> obtenerListaDeHabilidadesPorIds(List<Long> listadeHabilidadesPorIds) throws Exception {
        return validarExistenciaDeUnaListaDeHabilidadesPorId(listadeHabilidadesPorIds);
    }

    @Override
    public Habilidad obtenerHabilidadPorId(Long idHabilidad) {
        return habilidadRepositorio.findHabilidadById(idHabilidad);
    }

    private List<Habilidad> validarExistenciaDeUnaListaDeHabilidadesPorId(List<Long> listadeHabilidadesPorIds) throws Exception {
        List<Habilidad> listaDeHabilidad = new ArrayList<>();
        for (Long idHabilidad :listadeHabilidadesPorIds){
            if(habilidadRepositorio.existsById(idHabilidad)){
                Optional<Habilidad> habilidadEncontrada = habilidadRepositorio.findById(idHabilidad);
                listaDeHabilidad.add(habilidadEncontrada.get());
            }else{
                throw new Exception("No existe Habilidad con el id"+ idHabilidad);
            }
        }
        return listaDeHabilidad;
    }
}
