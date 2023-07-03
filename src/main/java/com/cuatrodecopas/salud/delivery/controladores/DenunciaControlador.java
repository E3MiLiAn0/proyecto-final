package com.cuatrodecopas.salud.delivery.controladores;

import com.cuatrodecopas.salud.core.entidades.Denuncia;
import com.cuatrodecopas.salud.core.servicios.interfaces.DenunciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/denuncia")
public class DenunciaControlador {

    @Autowired
    DenunciaServicio denunciaServicio;

    @ResponseBody
    @GetMapping(value = "/obtenerDenunciaPorId")
    public ResponseEntity<Map<String, Object>> listaUsuarios(@RequestParam(value = "idDenuncia") Long idDenuncia) {
        Denuncia denuncia = denunciaServicio.obtenerDenunciaPorId(idDenuncia);
        Map<String, Object> listaUsuariosMap = new HashMap<>();
        listaUsuariosMap.put("Denuncia", denuncia);
        return new ResponseEntity<>(listaUsuariosMap, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/obtenerTodasLasDenuncias")
    public ResponseEntity<Map<String, Object>> obtenerTodasLasDenuncias() {
        List<Denuncia> denuncia = denunciaServicio.obtenerTodasLasDenuncias();
        Map<String, Object> map = new HashMap<>();
        map.put("Denuncia", denuncia);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/obtenerTodasLasDenunciasPorIdUsuario")
    public ResponseEntity<Map<String, Object>> obtenerTodasLasDenunciasPorIdUsuario(@RequestParam(value = "idUsuario") Long idUsuario) {
        List<Denuncia> denuncia = denunciaServicio.obtenerTodasLasDenunciasPorIdUsuario(idUsuario);
        Map<String, Object> listaUsuariosMap = new HashMap<>();
        listaUsuariosMap.put("Denuncia", denuncia);
        return new ResponseEntity<>(listaUsuariosMap, HttpStatus.OK);
    }
}
