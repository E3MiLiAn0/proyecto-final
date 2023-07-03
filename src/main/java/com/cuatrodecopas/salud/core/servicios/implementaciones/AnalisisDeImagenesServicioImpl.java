package com.cuatrodecopas.salud.core.servicios.implementaciones;

import com.cuatrodecopas.salud.core.servicios.interfaces.AnalisisDeImagenesServicio;
import com.cuatrodecopas.salud.data.gateways.interfaces.AnalisisDeImagenesGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AnalisisDeImagenesServicioImpl implements AnalisisDeImagenesServicio {
    @Autowired
    AnalisisDeImagenesGateway analisisDeImagenesGateway;

    public AnalisisDeImagenesServicioImpl(AnalisisDeImagenesGateway analisisDeImagenesGateway) {
        this.analisisDeImagenesGateway=analisisDeImagenesGateway;
    }


 @Override
    public Boolean compararImagenes(MultipartFile rutaImagenOrigen, MultipartFile rutaImagenObjetivo) throws IOException {
        return analisisDeImagenesGateway.compararImagenes(rutaImagenOrigen,rutaImagenObjetivo);
    }

  /*
    private Integer detectarCantidadDeRostros(MultipartFile rutaImagenOrigen) throws IOException {

                DetectFacesRequest detectFacesRequest2 = new DetectFacesRequest().withImage(new Image().withBytes(ByteBuffer.wrap(rutaImagenOrigen.getBytes())));
                DetectFacesResult detectFacesResult2 = cliente.detectFaces(detectFacesRequest2);
        int numFacesTarget = detectFacesResult2.getFaceDetails().size();

        return numFacesTarget;
    }*/


}
