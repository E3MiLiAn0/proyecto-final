package com.cuatrodecopas.salud.data.gateways.interfaces;

import com.cuatrodecopas.salud.core.entidades.ArchivoS3Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface GestorDeArchivosGateway {
    List<ArchivoS3Imagen> obtenerListaArchivosS3(String nombreBucket);

    ArchivoS3Imagen obtenerArchivoS3PorNombre(String nombreBucket, String nombreArchivo) throws IOException;

    MultipartFile obtenerImagenPorNombre(String nombreBucket, String nombreArchivo) throws IOException;

    byte[] descargarArchivo(String nombreBucket, String nombreArchivo) throws IOException;

    void moverArchivo(String nombreBucket, String nombreArchivo, String nombreArchivoDestino);

    void borrarArchivo(String nombreBucket, String nombreArchivo);

    String subirArchivo(String nombreBucket, String nombreArchivo, File archivo);


}
