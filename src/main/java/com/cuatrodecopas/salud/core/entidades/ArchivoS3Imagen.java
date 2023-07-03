package com.cuatrodecopas.salud.core.entidades;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ArchivoS3Imagen {
    String nombre;
    String url;
}
