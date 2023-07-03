package com.cuatrodecopas.salud.delivery.controladores;

import com.cuatrodecopas.salud.core.entidades.Calificacion;
import com.cuatrodecopas.salud.core.servicios.interfaces.CalificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/calificacion")
public class CalificacionControlador {


    @Autowired
    CalificacionServicio calificacionServicio;

    @ResponseBody
    @GetMapping(value ="/obtenerCalificacionPorId")
    public ResponseEntity<Map<String, Object>> obtenerCalificacionPorId(@RequestParam(value = "idCalificacion") Long idCalificacion){
        try {
            Calificacion calificacion = calificacionServicio.obtenercalificacionPorId(idCalificacion);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje", "calificacion encontrada");
            mapa.put("calificacion", calificacion);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/calificacionPromedioDeUnProfesional")
    public ResponseEntity<Map<String, Object>> calificacionPromedioDeUnProfesional(@RequestParam(value = "idProfesional") Long idProfesional){
        try {
            Double calificacionPromedio = calificacionServicio.obtenerCalificacionPromedioDeUnProfesional(idProfesional);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("promedio calificacion profesional", calificacionPromedio);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/calificacionPromedioDeUnUsuario")
    public ResponseEntity<Map<String, Object>> calificacionPromedioDeUnUsuario(@RequestParam(value = "idUsuario") Long idUsuario){
        try {
            Double calificacionPromedio = calificacionServicio.obtenerCalificacionPromedioDeUnUsuario(idUsuario);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("promedio calificacion usuario", calificacionPromedio);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }


    @ResponseBody
    @PutMapping(value ="/profesionalCalificaUsuario")
    public ResponseEntity<Map<String, Object>> profesionalCalificaUsuario(@RequestParam(value = "id") Long idPrestacion,@RequestParam(value = "comentario") String comentario, @RequestParam(value = "puntaje") Integer puntaje) {

        try {
           calificacionServicio.profesionalCalificaUsuario(idPrestacion,comentario,puntaje);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje", "Calificacion Exitosa");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @PutMapping(value ="/usuarioCalificaProfesional")
    public ResponseEntity<Map<String, Object>> usuarioCalificaProfesional(@RequestParam(value = "idPrestacion") Long idPrestacion,
                                                                    @RequestParam(value = "comentario") String comentario,
                                                                    @RequestParam(value = "puntaje") Integer puntaje) {
        try {
            calificacionServicio.usuariolCalificaProfesiona(idPrestacion,comentario,puntaje);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje", "Calificacion Exitosa");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    private Map<String, Object> crearErrorDeRespouesta(String error) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", error);
        return response;
    }

}
