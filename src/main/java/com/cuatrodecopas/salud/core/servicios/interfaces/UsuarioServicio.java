package com.cuatrodecopas.salud.core.servicios.interfaces;

import com.cuatrodecopas.salud.core.entidades.Denuncia;
import com.cuatrodecopas.salud.core.entidades.Usuario;
import com.cuatrodecopas.salud.delivery.dtos.ValidarUsuariooDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsuarioServicio {
    String registrarUsuario(Usuario usuario, MultipartFile imagenDniFrente,MultipartFile imagenDniReverso,MultipartFile imagenSosteniendoDni) throws Exception;
    Usuario obtenerUsuarioPorMail(String mail) throws Exception;
    Usuario obtenerUsuarioPorDni(String dni) throws Exception;
    String loginUsuario(String mail, String password);
    List<Usuario> obtenerListaUsuario ();

    Boolean verificarQueNoExistaElUsuario(Usuario usuario) throws Exception;


    void validarUsuarioPorReconocimientoFacial(ValidarUsuariooDto usuarioDto) throws Exception;

    void usuarioRealizaDenuncia(Denuncia denuncia,MultipartFile evidencia) throws Exception;

    Usuario obtenerUsuarioPorId(Long idUsuario);
}
