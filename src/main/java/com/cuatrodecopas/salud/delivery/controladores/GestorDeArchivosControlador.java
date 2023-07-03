package com.cuatrodecopas.salud.delivery.controladores;

import com.cuatrodecopas.salud.core.entidades.ArchivoS3Imagen;
import com.cuatrodecopas.salud.core.servicios.interfaces.GestorDeArchivosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "api/archivos")
public class GestorDeArchivosControlador {

    private GestorDeArchivosServicio gestorDeArchivosServicio;

    @Autowired
    public GestorDeArchivosControlador(GestorDeArchivosServicio gestorDeArchivosServicio) {
        this.gestorDeArchivosServicio = gestorDeArchivosServicio;
    }

    @GetMapping("/obtenerArchivoPorNombre")
    public ResponseEntity<ArchivoS3Imagen> obtenerArchivoS3PorNombre(@RequestParam(value = "nombreBucket") String nombreBucket,
                                                                     @RequestParam(value = "nombreArchivo") String nombreArchivo) throws IOException {
        return new ResponseEntity<>(gestorDeArchivosServicio.obtenerArchivoS3PorNombre(nombreBucket, nombreArchivo), HttpStatus.OK);
    }

    @GetMapping("/obtenerListaArchivos")
    public ResponseEntity<List<ArchivoS3Imagen>> obtenerListaArchivosS3(@RequestParam(value = "nombreBucket") String nombreBucket) throws IOException {
        List<ArchivoS3Imagen> lista = new ArrayList<>();
        HttpStatus status = HttpStatus.OK;
        try {
            lista = gestorDeArchivosServicio.obtenerListaArchivosS3(nombreBucket);
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(lista, status);
    }

    @GetMapping("/descargarArchivo")
    public ResponseEntity<ByteArrayResource> descargarArchivoS3(@RequestParam(value = "nombreBucket") String nombreBucket,
                                                            @RequestParam(value = "nombreArchivo") String nombreArchivo)
            throws IOException {
        byte[] data = gestorDeArchivosServicio.descargarArchivoS3(nombreBucket, nombreArchivo);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + nombreArchivo + "\"")
                .body(resource);
    }

    @DeleteMapping("/borrarArchivo")
    public ResponseEntity<String> deleteFile(@RequestParam(value = "nombreBucket") String nombreBucket,
                                             @RequestParam(value = "nombreArchivo") String nombreArchivo) {
        gestorDeArchivosServicio.borrarArchivo(nombreBucket, nombreArchivo);
        return new ResponseEntity<>("Archivo Borrado", HttpStatus.OK);
    }

    @GetMapping("/MoverArchivo")
    public ResponseEntity<String> moverArchivo(@RequestParam(value = "nombreBucket") String nombreBucket,
                                           @RequestParam(value = "fileName") String fileKey,
                                           @RequestParam(value = "fileNameDest") String fileKeyDest) {
        gestorDeArchivosServicio.moverArchivo(nombreBucket, fileKey, fileKeyDest);
        return new ResponseEntity<>("Archivo movido", HttpStatus.OK);
    }
/*
    @PostMapping("/subirArchivo")
    public ResponseEntity<String> subirArchivo(@RequestParam(value = "nombreBucket") String nombreBucket,
                                             @RequestParam(value = "filePath") String filePath,
                                             @RequestParam(value = "archivo") MultipartFile archivo) {
        return new ResponseEntity<>(awsServicio.subirArchivo(nombreBucket, filePath, archivo), HttpStatus.OK);
    }*/


}
