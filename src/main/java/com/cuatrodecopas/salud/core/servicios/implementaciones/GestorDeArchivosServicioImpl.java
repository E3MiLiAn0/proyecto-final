package com.cuatrodecopas.salud.core.servicios.implementaciones;

import com.cuatrodecopas.salud.core.entidades.ArchivoS3Imagen;
import com.cuatrodecopas.salud.core.entidades.Denuncia;
import com.cuatrodecopas.salud.core.entidades.Usuario;
import com.cuatrodecopas.salud.core.servicios.interfaces.GestorDeArchivosServicio;
import com.cuatrodecopas.salud.data.gateways.interfaces.GestorDeArchivosGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class GestorDeArchivosServicioImpl implements GestorDeArchivosServicio {
    private GestorDeArchivosGateway gestorDeArchivosGateway;


    @Autowired
    public GestorDeArchivosServicioImpl(GestorDeArchivosGateway gestorDeArchivosGateway) {
        this.gestorDeArchivosGateway = gestorDeArchivosGateway;
    }
    @Override
    public List<ArchivoS3Imagen> obtenerListaArchivosS3(String nombreBucket) {
        return gestorDeArchivosGateway.obtenerListaArchivosS3(nombreBucket);
    }
    @Override
    public ArchivoS3Imagen obtenerArchivoS3PorNombre(String nombreBucket, String nombreArchivo) throws IOException {
        return gestorDeArchivosGateway.obtenerArchivoS3PorNombre(nombreBucket, nombreArchivo);
    }

    @Override
    public byte[] descargarArchivoS3(String nombreBucket, String nombreArchivo) throws IOException {
        return gestorDeArchivosGateway.descargarArchivo(nombreBucket, nombreArchivo);
    }

    @Override
    public void moverArchivo(String nombreBucket, String fileKey, String destinationFileKey) {
        gestorDeArchivosGateway.moverArchivo(nombreBucket, fileKey, destinationFileKey);
    }

    @Override
    public void borrarArchivo(String nombreBucket, String fileKey) {
        gestorDeArchivosGateway.borrarArchivo(nombreBucket, fileKey);
    }

    @Override
    public String subirArchivo(MultipartFile archivo, String imagenSolicitada, Usuario usuario) throws Exception {
        File fileObj = convertirMultiPartFileAFile(archivo);
        String nombreArchivo = generarNombreImagen(usuario,imagenSolicitada);
        return gestorDeArchivosGateway.subirArchivo("nursenear",nombreArchivo, fileObj);
    }


    private File convertirMultiPartFileAFile(MultipartFile archivo) throws Exception {
        File archivoConvertido = new File(archivo.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(archivoConvertido)) {
            fileOutputStream.write(archivo.getBytes());
        } catch (IOException e) {
            throw new Exception("Error convirtiendo multipartFile a file", e);
        }
        return archivoConvertido;
    }

    @Override
    public MultipartFile obtenerImagenPorNombre(String nombreBucket, String nombreArchivo) throws IOException {
        return gestorDeArchivosGateway.obtenerImagenPorNombre(nombreBucket,nombreArchivo);
    }

    @Override
    public String subirArchivoDenuncia(MultipartFile evidencia, Denuncia denuncia) throws Exception {
        String imagenSolicitada="Denuncia";
        File fileObj = convertirMultiPartFileAFile(evidencia);
        String nombreArchivo = generarNombreImagen(denuncia.getUsuario(),imagenSolicitada);
        return gestorDeArchivosGateway.subirArchivo("nursenear",nombreArchivo, fileObj);
    }

    public String generarNombreImagen(Usuario usuario,String imagenSolicitada){
        String fechaActual = generarFechaActual();
        String nombreImagen = imagenSolicitada +"_"+usuario.getNombre()+"_"+usuario.getApellido()+"_"+usuario.getDni()+"_"+fechaActual +".jpeg";
        return nombreImagen;
    }


    private String generarFechaActual() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHoraFormateada = fechaHoraActual.format(formatter);
        return fechaHoraFormateada;
    }

    private static Date toDate(String fecha, String formato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        LocalDateTime localDateTime = LocalDateTime.parse(fecha, formatter);
        return java.sql.Timestamp.valueOf(localDateTime);
    }
}
