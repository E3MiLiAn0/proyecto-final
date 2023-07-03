package com.cuatrodecopas.salud.data.gateways.implementaciones;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.cuatrodecopas.salud.core.entidades.ArchivoS3Imagen;
import com.cuatrodecopas.salud.data.gateways.interfaces.GestorDeArchivosGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class S3GestorDeArchivosGatewayImpl implements GestorDeArchivosGateway {
    private AmazonS3 s3Cliente;

    @Autowired
    public S3GestorDeArchivosGatewayImpl(@Qualifier("s3Client") AmazonS3 s3Cliente) {
        this.s3Cliente = s3Cliente;
    }
    @Override
    public List<ArchivoS3Imagen> obtenerListaArchivosS3(String nombreBucket) {
        List<ArchivoS3Imagen>  items = s3Cliente.listObjectsV2(nombreBucket).getObjectSummaries().stream()
                                        .parallel()
                                        .map(S3ObjectSummary::getKey)
                                        .map(key -> mapS3ToObject(nombreBucket, key))
                                        .collect(Collectors.toList());
        return items;
    }

    @Override
    public ArchivoS3Imagen obtenerArchivoS3PorNombre(String nombreBucket, String nombreArchivo) throws IOException {
        if (!s3Cliente.doesBucketExist(nombreBucket)) {
            return null;
        }
        S3Object s3Object = s3Cliente.getObject(nombreBucket, nombreArchivo);
        return  ArchivoS3Imagen.builder()
                .nombre(s3Object.getKey())
                .url(s3Cliente.getUrl(nombreBucket, nombreArchivo).toString())
                .build();
    }

    @Override
    public MultipartFile obtenerImagenPorNombre(String nombreBucket, String nombreArchivo) throws IOException {
        if (!s3Cliente.doesBucketExist(nombreBucket)) {
            return null;
        }
        S3Object s3Object = s3Cliente.getObject(nombreBucket, nombreArchivo);
        return convertS3ObjectToMultipartFile(s3Object);
    }

    public MultipartFile convertS3ObjectToMultipartFile(S3Object s3Object) throws IOException {
        String filename = s3Object.getKey();
        String contentType = s3Object.getObjectMetadata().getContentType();
        InputStream inputStream = s3Object.getObjectContent();

        // Crea un MultipartFile utilizando el InputStream y los metadatos del objeto S3
        MultipartFile multipartFile = new MockMultipartFile(filename, filename, contentType, inputStream);
        return multipartFile;
    }

    @Override
    public byte[] descargarArchivo(String nombreBucket, String nombreArchivo) {
        S3Object s3Object = s3Cliente.getObject(nombreBucket, nombreArchivo);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return  IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void moverArchivo(String nombreBucket, String nombreArchivo, String nombreArchivoDestino) {
        CopyObjectRequest copiarObjetoRequerido = new CopyObjectRequest(nombreBucket, nombreArchivo, nombreBucket, nombreArchivoDestino);
        s3Cliente.copyObject(copiarObjetoRequerido);
        borrarArchivo(nombreBucket, nombreArchivo);
    }

    @Override
    public void borrarArchivo(String nombreBucket, String nombreArchivo) {
        s3Cliente.deleteObject(nombreBucket, nombreArchivo);
    }

    @Override
    public String subirArchivo(String nombreBucket, String nombreArchivo, File archivo) {
        s3Cliente.putObject(new PutObjectRequest(nombreBucket, nombreArchivo, archivo));
        archivo.delete();
        return nombreArchivo;
    }

    private ArchivoS3Imagen mapS3ToObject(String nombreBucket, String key) {
        return ArchivoS3Imagen.builder()
                .nombre(s3Cliente.getObjectMetadata(nombreBucket, key).getUserMetaDataOf("name"))
                .url(s3Cliente.getUrl(nombreBucket, key).toString())
                .build();
    }

}
