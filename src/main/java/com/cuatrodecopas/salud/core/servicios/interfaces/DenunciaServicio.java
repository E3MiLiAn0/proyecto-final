package com.cuatrodecopas.salud.core.servicios.interfaces;

import com.cuatrodecopas.salud.core.entidades.Denuncia;

import java.util.List;

public interface DenunciaServicio {
    Denuncia obtenerDenunciaPorId(Long idDenuncia);

    List<Denuncia> obtenerTodasLasDenuncias();

    List<Denuncia> obtenerTodasLasDenunciasPorIdUsuario(Long idUsuario);
}
