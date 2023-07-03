package com.cuatrodecopas.salud.delivery.controladores;

import com.cuatrodecopas.salud.core.entidades.*;
import com.cuatrodecopas.salud.core.servicios.interfaces.*;
import com.cuatrodecopas.salud.delivery.dtos.InformacionDeUnaHabilidadDeUnProfesionalDto;
import com.cuatrodecopas.salud.delivery.dtos.ProfesionalEnGuardiaDto;
import com.cuatrodecopas.salud.delivery.dtos.ProfesionalRegistroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/profesional")
public class ProfesionalControlador {
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private PrestacionServicio prestacionServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private HabilidadServicio habilidadServicio;
    @Autowired
    private ProfesionalHabilidadServicio profesionalHabilidadServicio;


    public ProfesionalControlador(ProfesionalServicio profesionalServicio,PrestacionServicio prestacionServicio) {
        this.profesionalServicio = profesionalServicio;
        this.prestacionServicio = prestacionServicio;
    }


    @ResponseBody
    @GetMapping("/validarRegistroProfesional")
    public String validarRegistroProfesional(@RequestParam(value = "matricula") String matricula,
                                            @RequestParam(value = "dniProfesional") String dniProfesional) {
        return profesionalServicio.validarRegistroProfesional(matricula,dniProfesional);
    }

    @ResponseBody
    @PostMapping(value = "/registroProfesional")
    public ResponseEntity<Map<String, Object>> registroProfesional(@Valid @RequestBody ProfesionalRegistroDTO profesionalRegistroDTO) throws Exception {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorDni(profesionalRegistroDTO.getDni());
            List<Habilidad> listaDeHabilidades = habilidadServicio.obtenerListaDeHabilidadesPorIds(profesionalRegistroDTO.getListaDeHabilidades());
            profesionalServicio.registrarProfesional(profesionalRegistroDTO.profesionalRegistroDtoAProfesional(usuario,listaDeHabilidades));
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje", "profesional dado de alta");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }


    @ResponseBody
    @GetMapping(value ="/obtenerUnProfesionalPorId")
    public ResponseEntity<Map<String, Object>> obtenerUnProfesionalPorId(@RequestParam(value = "idProfesional") Long idProfesional){
        try {
            Profesional profesional = profesionalServicio.obtenerProfesionalPorId(idProfesional);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("profesional",profesional);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/obtenerTodasLasHabilidadesDeUnProfesionalPorId")
    public ResponseEntity<Map<String, Object>> obtenerTodasLasHabilidadesDeUnProfesionalPorId(@RequestParam(value = "idProfesional") Long idProfesional){
        try {
            List<ProfesionalHabilidad> listaDeHabilidades = profesionalHabilidadServicio.obtenerTodasLasHabilidadesPorIdDeUnProfesional(idProfesional);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("listaDeHabilidades",listaDeHabilidades);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/ObtenerProfesionalesPorLocalidadYHabilidad")
    public ResponseEntity<Map<String, Object>> ObtenerProfesionalesPorLocalidadYHabilidad(@RequestParam(value = "localidad") String localidad, @RequestParam(value = "idHabilidad") Long idHabilidad){
        try {
            List<Profesional> listaDeProfesionales = profesionalServicio.filtrarProfesionalesPorLocalidadYHabilidad(localidad,idHabilidad);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("listaDeProfesionales",listaDeProfesionales);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @PutMapping(value = "/actualizarGuardia")
    public ResponseEntity<Map<String, Object>> actualizarGuardia(@Valid @RequestBody ProfesionalEnGuardiaDto profesionalEnGuardiaDto)  {
        try {
            profesionalServicio.actualizarGuardia(profesionalEnGuardiaDto.profesionalEnGuardiaDtoAProfesional(profesionalEnGuardiaDto));
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje", "usuario en guardia");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/listaPrestacionesPendienteDeUnProfesional")
    public ResponseEntity<Map<String, Object>> listaPrestacionesPendienteDeUnProfesional(@RequestParam(value = "dni") String dni){
        try {
           List<Prestacion> listaPrestaciones = prestacionServicio.listaDeSolicitudesDePrestacionesDeUnProfesionalPendientes(dni);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("listaPrestaciones",listaPrestaciones);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping(value ="/confirmarPrestacion")
    public ResponseEntity<Map<String, Object>> confirmarPrestacion(@RequestParam(value = "id") Long idPrestacion, @RequestParam(value = "confirmacion") Boolean confirmacion){
        try {
            prestacionServicio.confirmarPrestacion(idPrestacion,confirmacion);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje", "Prestacion Confirmada");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/listaProfesionalEnGuardiaYDisponible")
    public ResponseEntity<Map<String, Object>> listaProfesionalEnGuardiaYDisponible(){
        try {
            List<Profesional> listaProfesional = profesionalServicio.obtenerListProfesionalesConGuardiaActivaYDisponible();
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("listaProfesional",listaProfesional);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @GetMapping(value ="/listaProfesionalEnGuardiaYDisponiblePorLocalidad")
    public ResponseEntity<Map<String, Object>> listaProfesionalEnGuardiaYDisponiblePorLocalidad(@RequestParam(value = "localidad") String localidad){
        try {
            List<Profesional> listaProfesional = profesionalServicio.obtenerListProfesionalesConGuardiaActivaYDisponiblePorLocalidad(localidad);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("listaProfesional",listaProfesional);
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }


    @ResponseBody
    @GetMapping(value ="/obtenerInformacionDeUnaHabilidadPorIdProfesional")
    public ResponseEntity<Map<String, Object>> obtenerInformacionDeUnaHabilidadPorIdProfesional(@RequestParam(value = "idProfesional") Long idProfesional,@RequestParam(value = "idHabilidad") Long idHabilidad ){
        try {
            InformacionDeUnaHabilidadDeUnProfesionalDto informacionDeUnaHabilidadDeUnProfesionalDto= new InformacionDeUnaHabilidadDeUnProfesionalDto();
            Profesional profesional= profesionalServicio.obtenerProfesionalPorId(idProfesional);
            Habilidad habilidad = habilidadServicio.obtenerHabilidadPorId(idHabilidad);
            Double precioQueCobraProfesionalPorHabilidad =profesionalHabilidadServicio.precioQueCobraProfesionalPorHabilidad(idHabilidad , idProfesional);
            Integer cantidadDeVecesRealizadas= prestacionServicio.cantidadDeVecesRealizoUnaHabilidad( idProfesional,idHabilidad);
            informacionDeUnaHabilidadDeUnProfesionalDto.armarDto(profesional,habilidad.getDescripcion(),precioQueCobraProfesionalPorHabilidad,cantidadDeVecesRealizadas);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("informacion de habilidad de un profesional",informacionDeUnaHabilidadDeUnProfesionalDto);
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
