package com.cuatrodecopas.salud.delivery.controladores;

import com.cuatrodecopas.salud.core.servicios.interfaces.AnalisisDeImagenesServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



@RestController
@RequestMapping(value = "/api/analisisImagenes")
public class AnalisisDeImagenesControlador {

    @Autowired
    private AnalisisDeImagenesServicio analisisDeImagenesServicio;

    @ResponseBody
    @PostMapping("/compararImagenes")
    public ResponseEntity<String> compararImagenes(@RequestParam("sourceImage") MultipartFile sourceImage,
                                                   @RequestParam("targetImage") MultipartFile targetImage) {
        try {
            Boolean similitud = analisisDeImagenesServicio.compararImagenes(sourceImage, targetImage);
            return ResponseEntity.ok("Similitud: " + similitud);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error comparando imagenes");
        }
    }
}
