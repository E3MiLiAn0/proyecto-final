package com.cuatrodecopas.salud.delivery.controladores;

import com.cuatrodecopas.salud.core.entidades.Usuario;
import com.cuatrodecopas.salud.core.servicios.implementaciones.AutenticacionServicioImpl;
import com.cuatrodecopas.salud.core.servicios.implementaciones.JwtServicioImpl;
import com.cuatrodecopas.salud.core.servicios.implementaciones.UsuarioServicioImpl;
import com.cuatrodecopas.salud.core.servicios.interfaces.AnalisisDeImagenesServicio;
import com.cuatrodecopas.salud.core.servicios.interfaces.ProfesionalServicio;
import com.cuatrodecopas.salud.delivery.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsuarioControlador {

    @Autowired
    UsuarioServicioImpl usuarioServicio;

    @Autowired
    PasswordEncoder passwordEncoder;
    private AutenticacionServicioImpl autenticacionServicio;
    private AuthenticationManager authenticationManager;
    private JwtServicioImpl jwtTokenUtil;
    private ProfesionalServicio profesionalServicio;

    private AnalisisDeImagenesServicio analisisDeImagenesServicio;

    @Autowired
    public UsuarioControlador(
            AutenticacionServicioImpl autenticacionServicio,
            AuthenticationManager authenticationManager,
            JwtServicioImpl jwtTokenUtil) {
        this.autenticacionServicio = autenticacionServicio;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @ResponseBody
    @GetMapping(value = "/listaUsuarios")
    public ResponseEntity<Map<String, Object>> listaUsuarios() {
        List<Usuario> listaUsuarios = usuarioServicio.obtenerListaUsuario();
        Map<String, Object> listaUsuariosMap = new HashMap<>();
        listaUsuariosMap.put("listaUsuarios", listaUsuarios);
        return new ResponseEntity<>(listaUsuariosMap, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "/registrarUsuario")
    public ResponseEntity<Map<String, Object>> registrarUsuario(@Valid @ModelAttribute UsuarioRegistroDto usuarioDto) {
        try {
            String passEncode = passwordEncoder.encode(usuarioDto.getPassword());
            String mensaje = usuarioServicio.registrarUsuario(usuarioDto.UsuarioRegistroDtoAUsuario(passEncode),usuarioDto.getImagenDniFrente(),usuarioDto.getImagenDniReverso(),usuarioDto.getImagenSosteniendoDni());
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje",mensaje);
            return new ResponseEntity<>(mapa, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping(value = "/validarUsuarioPorReconocimientoFacial")
    public ResponseEntity<Map<String, Object>> validarUsuarioPorReconocimientoFacial(@Valid @ModelAttribute ValidarUsuariooDto usuarioDto) {
        try {
             usuarioServicio.validarUsuarioPorReconocimientoFacial(usuarioDto);
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje","Reconocimiento facial exitoso");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", e.toString());
            response.put("mensaje", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);


        }
    }


    @ResponseBody
    @PostMapping(value = "/loginUsuario")
    public ResponseEntity<Map<String, Object>> loginUsuario( @RequestBody UsuarioLoginDto usuarioLoginDto)  {
        UserDetails userDetails;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuarioLoginDto.getMail(),
                            usuarioLoginDto.getPassword()
                    )
            );
            userDetails = (UserDetails) auth.getPrincipal();
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            Usuario usuarioLogueado = usuarioServicio.obtenerUsuarioPorMail( usuarioLoginDto.getMail());

            //String resultado = usuarioServicio.loginUsuario(usuarioLoginDto.getMail(),usuarioLoginDto.getPassword());

            Map<String, Object> resultadoLoginMap = new HashMap<>();
            resultadoLoginMap.put("token", new AutenticacionRespuestaDto(jwt));
            resultadoLoginMap.put("usuario",usuarioLogueado );
            return new ResponseEntity<>(resultadoLoginMap, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(crearErrorDeRespouesta(e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping(value = "/usuarioRealizaDenuncia")
    public ResponseEntity<Map<String, Object>> realizarDenuncia(@Valid @ModelAttribute DenunciaDto denunciaDto) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorDni(denunciaDto.getDniUsuario());
            usuarioServicio.usuarioRealizaDenuncia(denunciaDto.pasarDenunciaDtoADenuncia(usuario),denunciaDto.getEvidencia());
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("mensaje","mensaje");
            return new ResponseEntity<>(mapa, HttpStatus.CREATED);
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
