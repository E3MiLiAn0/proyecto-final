package com.cuatrodecopas.salud.data.gateways.implementaciones;

import com.cuatrodecopas.salud.data.gateways.interfaces.ValidacionRegistroProfesionalGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SisaValidacionRegistroProfesionalGatewayImpl implements ValidacionRegistroProfesionalGateway {

    @Value("${baseDedatosProfesional}")
    private String baseDedatosProfesional;
    @Override
    public String buscarProfesionalPorMatricula(String matricula,String dniProfesional) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(baseDedatosProfesional+"?matricula="+matricula+"&numeroDocumento="+dniProfesional, String.class);
        return response.getBody();
    }

}
