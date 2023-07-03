package com.cuatrodecopas.salud.delivery.controladores;

import com.cuatrodecopas.salud.core.entidades.Prestacion;
import com.cuatrodecopas.salud.core.servicios.interfaces.MetricasServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/metrica")
public class MetricasControlador {

    @Autowired
    private MetricasServicio metricasServicio;

    @ResponseBody
    @GetMapping(value ="/obtenerMetricaSegunHabilidadTiempoDeUnProfesional")
    public ResponseEntity<Map<String, Object>> obtenerMetricaSegunHabilidadTiempoDeUnProfesional(@RequestParam(value = "idProfesional") Long idProfesional,
                                                                                                 @RequestParam(value = "idHabilidad") Long idHabilidad,
                                                                                                @RequestParam(value = "mes") Integer mes){
        try {
            List<Prestacion> listaPrestaciones = metricasServicio.obtenerMetricaSegunHabilidadTiempoDeUnProfesional(idProfesional,idHabilidad,mes);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("listaPrestaciones", listaPrestaciones);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/findByProfesionalYHabilidadOrdenadoPorPuntajeDeProfesional")
    public ResponseEntity<Map<String, Object>> findByProfesionalYHabilidadOrdenadoPorPuntajeDeProfesional(@RequestParam(value = "idProfesional") Long idProfesional,
                                                                                                          @RequestParam(value = "idHabilidad") Long idHabilidad){
        try {
            List<Prestacion> listaPrestaciones = metricasServicio.findByProfesionalYHabilidadOrdenadoPorPuntajeDeProfesional(idProfesional,idHabilidad);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("listaPrestaciones", listaPrestaciones);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }
    private Map<String, Object> crearErrorDeRespouesta(String error) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", error);
        return response;
    }
}
