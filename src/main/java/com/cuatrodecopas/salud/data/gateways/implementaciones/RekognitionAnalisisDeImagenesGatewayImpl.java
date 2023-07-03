package com.cuatrodecopas.salud.data.gateways.implementaciones;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.cuatrodecopas.salud.data.gateways.interfaces.AnalisisDeImagenesGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

@Component
public class RekognitionAnalisisDeImagenesGatewayImpl implements AnalisisDeImagenesGateway {

    @Autowired
    private AmazonRekognition cliente;

    @Override
    public Boolean compararImagenes(MultipartFile rutaImagenOrigen, MultipartFile rutaImagenObjetivo) throws IOException {
        try {
            CompareFacesRequest request = new CompareFacesRequest()
                    .withSourceImage(new Image().withBytes(ByteBuffer.wrap(rutaImagenOrigen.getBytes())))
                    .withTargetImage(new Image().withBytes(ByteBuffer.wrap(rutaImagenObjetivo.getBytes())));

            CompareFacesResult resultado = cliente.compareFaces(request);
            List<ComparedFace> unmatchedFaces = resultado.getUnmatchedFaces();
            List<CompareFacesMatch> faceDetails = resultado.getFaceMatches();
            return validarCaras(faceDetails, unmatchedFaces);

        } catch (IOException e) {
            throw new IOException("Error al comparar las imágenes", e);
        }
    }


    Boolean validarCaras(List<CompareFacesMatch> resultado, List<ComparedFace> unmatchedFaces) throws IOException {
        if (resultado.isEmpty() || unmatchedFaces.size() > 0) {
            throw new IOException("Error: Imagenes no coinciden");
        } else {
            Float similitudMinima=90f;
            for (CompareFacesMatch faceMatchNode : resultado){
                Float similitud = faceMatchNode.getSimilarity();
                if(similitud < similitudMinima){
                    throw new IOException("Error: Imagenes no coinciden");
                }
            }
            return true;
        }
    }
}
