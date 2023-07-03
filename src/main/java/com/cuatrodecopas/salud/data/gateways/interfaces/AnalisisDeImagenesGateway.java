package com.cuatrodecopas.salud.data.gateways.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AnalisisDeImagenesGateway {
    Boolean compararImagenes(MultipartFile rutaImagenOrigen, MultipartFile rutaImagenObjetivo) throws IOException;

}
