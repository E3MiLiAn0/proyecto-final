package com.cuatrodecopas.salud.delivery.controladores;

import com.cuatrodecopas.salud.core.entidades.Habilidad;
import com.cuatrodecopas.salud.core.entidades.Prestacion;
import com.cuatrodecopas.salud.core.entidades.Profesional;
import com.cuatrodecopas.salud.core.entidades.Usuario;
import com.cuatrodecopas.salud.core.servicios.interfaces.*;
import com.cuatrodecopas.salud.delivery.dtos.PrestacionCreacionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestacion")
public class PrestacionControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    ProfesionalServicio profesionalServicio;

    @Autowired
    PrestacionServicio prestacionServicio;

    @Autowired
    HabilidadServicio habilidadServicio;

    @Autowired
    ProfesionalHabilidadServicio profesionalHabilidadServicio;

    @ResponseBody
    @PostMapping(value ="/crearPrestacion")
    public ResponseEntity<Map<String, Object>> crearPrestacion(@Valid @RequestBody PrestacionCreacionDto prestacionCreacionDto ){
        try {
            Habilidad habilidad = habilidadServicio.obtenerHabilidadPorId(prestacionCreacionDto.getIdHabilidad());
            Usuario clienteEncontrado = usuarioServicio.obtenerUsuarioPorDni(prestacionCreacionDto.getDniUsuario());
            Profesional profesionalEncontrado = profesionalServicio.obtenerProfesionalPorDni(prestacionCreacionDto.getDniProfesional());
           Double precioQueCobraProfesional = profesionalHabilidadServicio.precioQueCobraProfesionalPorHabilidad(profesionalEncontrado.getId(),habilidad.getId());
            Prestacion prestacion = prestacionCreacionDto.prestacionCreacionDtoAPretacion(clienteEncontrado,profesionalEncontrado,habilidad,precioQueCobraProfesional);
            prestacionServicio.crearPrestacion(prestacion);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje", "Prestacion Creada");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/obtenerUltimaPrestacionSolicitadaPorElUsuario")
    public ResponseEntity<Map<String, Object>> obtenerUltimaPrestacionSolicitadaPorElUsuario(@RequestParam(value = "dniUsuario") String dniUsuario){
        try {
            Prestacion prestacion = prestacionServicio.obtenerUltimaPrestacionSolicitadaPorElUsuario(dniUsuario);
            System.out.println(prestacion.getFechaCreada());
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("prestacion", prestacion);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/obtenerPrestacionActivaPorDniUsuario")
    public ResponseEntity<Map<String, Object>> obtenerPrestacionActivaPorDniUsuario(@RequestParam(value = "dniUsuario") String dniUsuario){
        try {
            Prestacion prestacion = prestacionServicio.obtenerPrestacionActivaPorDniDelUsuario(dniUsuario);
            System.out.println(prestacion.getFechaCreada());
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("prestacion", prestacion);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/obtenerPrestacionActivaPorDniProfesional")
    public ResponseEntity<Map<String, Object>> obtenerPrestacionActivaPorDniProfesional(@RequestParam(value = "dniProfesional") String dniProfesional){
        try {
            Prestacion prestacion = prestacionServicio.obtenerPrestacionActivaPorDniDelProfesional(dniProfesional);
            System.out.println(prestacion.getFechaCreada());
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("prestacion", prestacion);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/obtenerPrestacionesPorIdUsuario")
    public ResponseEntity<Map<String, Object>> obtenerPrestacionesPorIdUsuario(@RequestParam(value = "id") Long id){
        try {
            List<Prestacion> prestaciones = prestacionServicio.obtenerListaDePrestacionesPorIdUsuario(id);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("prestacion", prestaciones);
            return new ResponseEntity<>(mapa, HttpStatus.OK);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/obtenerPrestacionesPorIdProfesional")
    public ResponseEntity<Map<String, Object>> obtenerPrestacionesPorIdProfesional(@RequestParam(value = "id") Long  id){
        try {
            List<Prestacion> prestaciones = prestacionServicio.obtenerListaDePrestacionesPorIdProfesional(id);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("prestacion", prestaciones);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/obtenerPrestacionPorId")
    public ResponseEntity<Map<String, Object>> obtenerPrestacionPorId(@RequestParam(value = "id") Long id){
        try {
            Prestacion prestacion = prestacionServicio.obtenerPrestacionPorId(id);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("prestacion", prestacion);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }


    @ResponseBody
    @PutMapping(value ="/pagarPrestacionPorId")
    public ResponseEntity<Map<String, Object>> pagarPrestacionPorId(@RequestParam(value = "id") Long id){
        try {
            prestacionServicio.pagarPrestacion(id);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje", "prestacion pagada");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @PutMapping(value ="/finalizarPrestacionPorId")
    public ResponseEntity<Map<String, Object>> finalizarPrestacionPorId(@RequestParam(value = "id") Long id){
        try {
             prestacionServicio.finalizarPrestacionActiva(id);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje", "prestacion finalizada con exito");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @PutMapping(value ="/cancelarUnaPrestacionPorId")
    public ResponseEntity<Map<String, Object>> cancelarUnaPrestacionPorId(@RequestParam(value = "idPrestacion") Long idPrestacion){
        try {
            prestacionServicio.cancelarUnaPrestacionPendienteDeUnProfesional(idPrestacion);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje", "prestacion cancelada con exito");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/obtenerPrestacionesPorIdProfesionalYFinalizadas")
    public ResponseEntity<Map<String, Object>> obtenerPrestacionesPorIdProfesionalYFinalizadas(@RequestParam(value = "idProfesional") Long idProfesional){
        try {
            List<Prestacion> listaPretaciones = prestacionServicio.obtenerPrestacionesFinalizadasPorUnProfesional(idProfesional);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("listaPretaciones", listaPretaciones);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }


    @ResponseBody
    @GetMapping(value ="/obtenerPrestacionesPorIdUsuarioYFinalizadas")
    public ResponseEntity<Map<String, Object>> obtenerPrestacionesPorIdUsuarioYFinalizadas(@RequestParam(value = "idUsuario") Long idUsuario){
        try {
            List<Prestacion> listaPretaciones = prestacionServicio.obtenerPrestacionesFinalizadasPorUnUsuario(idUsuario);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("listaPretaciones", listaPretaciones);
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
