package com.cuatrodecopas.salud.core.servicios.implementaciones;

import com.cuatrodecopas.salud.core.entidades.Denuncia;
import com.cuatrodecopas.salud.core.servicios.interfaces.DenunciaServicio;
import com.cuatrodecopas.salud.data.repositorios.DenunciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaServicioImpl implements DenunciaServicio {

    @Autowired
    DenunciaRepositorio denunciaRepositorio;
    @Override
    public Denuncia obtenerDenunciaPorId(Long idDenuncia) {
        return denunciaRepositorio.findDenunciaById(idDenuncia);
    }

    @Override
    public List<Denuncia> obtenerTodasLasDenuncias() {
        return denunciaRepositorio.findAll();
    }

    @Override
    public List<Denuncia> obtenerTodasLasDenunciasPorIdUsuario(Long idUsuario) {
        return denunciaRepositorio.findByUsuarioId(idUsuario);
    }
}
