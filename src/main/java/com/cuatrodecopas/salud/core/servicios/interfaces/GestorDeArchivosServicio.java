package com.cuatrodecopas.salud.core.servicios.interfaces;


import com.cuatrodecopas.salud.core.entidades.ArchivoS3Imagen;
import com.cuatrodecopas.salud.core.entidades.Denuncia;
import com.cuatrodecopas.salud.core.entidades.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GestorDeArchivosServicio {

    ArchivoS3Imagen obtenerArchivoS3PorNombre(String nombreBucket, String nombreArchivo) throws IOException;

    /*ArchivoS3Imagen obtenerArchivoS3PorDniEImagenSolicitada(String dni, String imagenSolicitada);*/
    List<ArchivoS3Imagen> obtenerListaArchivosS3(String nombreBucket) throws IOException;

    byte[] descargarArchivoS3(String nombreBucket, String nombreArchivo) throws IOException;

    void moverArchivo(String nombreBucket, String nombreArchivo, String nombreArchivoDestino);

    void borrarArchivo(String nombreBucket, String nombreArchivo);

    String subirArchivo(MultipartFile archivo, String imagenSolicitada, Usuario usuario) throws Exception;

    String generarNombreImagen(Usuario usuario,String imagenSolicitada);

    MultipartFile obtenerImagenPorNombre(String nombreBucket, String nombreArchivo) throws IOException;

    String subirArchivoDenuncia(MultipartFile evidencia, Denuncia denuncia) throws Exception;
}
