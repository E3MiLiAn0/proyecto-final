package com.cuatrodecopas.salud.core.servicios.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AnalisisDeImagenesServicio {
    Boolean compararImagenes(MultipartFile foto1, MultipartFile foto2) throws IOException;
}
