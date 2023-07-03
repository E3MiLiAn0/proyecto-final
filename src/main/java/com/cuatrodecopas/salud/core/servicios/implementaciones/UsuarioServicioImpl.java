package com.cuatrodecopas.salud.core.servicios.implementaciones;

import com.cuatrodecopas.salud.core.entidades.ArchivoS3Imagen;
import com.cuatrodecopas.salud.core.entidades.Denuncia;
import com.cuatrodecopas.salud.core.entidades.Usuario;
import com.cuatrodecopas.salud.core.servicios.interfaces.AnalisisDeImagenesServicio;
import com.cuatrodecopas.salud.core.servicios.interfaces.GestorDeArchivosServicio;
import com.cuatrodecopas.salud.core.servicios.interfaces.UsuarioServicio;
import com.cuatrodecopas.salud.data.gateways.interfaces.AutenticacionUsuarioGateway;
import com.cuatrodecopas.salud.data.repositorios.DenunciaRepositorio;
import com.cuatrodecopas.salud.data.repositorios.UsuarioRepositorio;
import com.cuatrodecopas.salud.delivery.dtos.ValidarUsuariooDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    AutenticacionUsuarioGateway autenticacionUsuarioGateway;

    @Autowired
    AnalisisDeImagenesServicio analisisDeImagenesServicio;

    @Autowired
    GestorDeArchivosServicio gestorDeArchivosServicio;

    @Autowired
    DenunciaRepositorio denunciaRepositorio;
    public List<Usuario> obtenerListaUsuario (){
        List<Usuario> listaUsuario= usuarioRepositorio.findAll();
        return listaUsuario;
    }

    @Transactional
    public String registrarUsuario (Usuario usuario,MultipartFile archivoImagenDniFrente,MultipartFile archivoImagenDniReverso,MultipartFile archivoImagenSosteniendoDni) throws Exception {
        try {
            Boolean usuarioVerificado = analisisDeImagenesServicio.compararImagenes(archivoImagenDniFrente,archivoImagenSosteniendoDni);
            if(usuarioVerificado){
                guardarUsuario(usuario);
                autenticacionUsuarioGateway.registrarUsuario(usuario);
                subirArchivos(usuario,archivoImagenDniFrente,archivoImagenDniReverso,archivoImagenSosteniendoDni);
            }
            return "registro exitoso";
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Usuario obtenerUsuarioPorMail(String mail) throws Exception {
        if(usuarioRepositorio.findByMail(mail) != null){
            return usuarioRepositorio.findByMail(mail);
        }
        throw new Exception("No existe Usuario con ese Mail");
    }

    @Override
    public Usuario obtenerUsuarioPorDni(String dni) throws Exception {
        if(usuarioRepositorio.findByDni(dni) != null){
            return usuarioRepositorio.findByDni(dni);
        }
        throw new Exception("No existe Usuario con ese DNI");
    }

    @Override
    public String loginUsuario(String mail, String password) {
        return autenticacionUsuarioGateway.loginUsuario(mail,password);
    }

    public Boolean verificarQueNoExistaElUsuario(Usuario usuario) throws Exception {
        Usuario usuarioEncontrado = usuarioRepositorio.findByDni(usuario.getDni());
        if (usuarioEncontrado == null) {
            return true;
        }
        throw new Exception("ya existe usuario con ese dni");
    }

    @Override
    public void validarUsuarioPorReconocimientoFacial(ValidarUsuariooDto usuarioDto) throws Exception {
        Usuario usuarioEncontrado = obtenerUsuarioPorDni(usuarioDto.getDni());
        String nombreDeImagen= "imagenDniFrente_"+usuarioEncontrado.getNombre()+"_"+usuarioEncontrado.getApellido()+"_"+usuarioEncontrado.getDni()+".jpeg";
        MultipartFile fotoDNI= gestorDeArchivosServicio.obtenerImagenPorNombre("nursenear", nombreDeImagen);
        analisisDeImagenesServicio.compararImagenes(usuarioDto.getSelfie(),fotoDNI);
    }

    @Override
    public void usuarioRealizaDenuncia(Denuncia denuncia,MultipartFile evidencia) throws Exception {
        System.out.println(evidencia.getOriginalFilename());
        System.out.println(evidencia.getName());
        System.out.println(evidencia.isEmpty());
        System.out.println(evidencia.getSize());
        System.out.println();

        if (!evidencia.isEmpty()){
            String nombreDenuncia = subirArchivoDenuncia(denuncia,evidencia);
            ArchivoS3Imagen archivo = gestorDeArchivosServicio.obtenerArchivoS3PorNombre("nursenear", nombreDenuncia);
            denuncia.setEvidencia(archivo.getUrl());
        }
        denunciaRepositorio.save(denuncia);
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepositorio.findUsuarioById(idUsuario);
    }

    private String subirArchivoDenuncia(Denuncia denuncia, MultipartFile evidencia) throws Exception {
        return gestorDeArchivosServicio.subirArchivoDenuncia(evidencia,denuncia);
    }


    private void subirArchivos(Usuario usuario,MultipartFile archivoImagenDniFrente,MultipartFile archivoImagenDniReverso,MultipartFile archivoImagenSosteniendoDni) throws Exception {
        String dniFrente = "imagenDniFrente";
        String dniReverso = "ImagenDniReverso";
        String imagenSosteniendoDni = "ImagenSosteniendoDni";
        try {
            gestorDeArchivosServicio.subirArchivo(archivoImagenDniFrente,dniFrente, usuario);
            gestorDeArchivosServicio.subirArchivo(archivoImagenDniReverso,dniReverso , usuario);
            gestorDeArchivosServicio.subirArchivo(archivoImagenSosteniendoDni,imagenSosteniendoDni, usuario);
            asignarUrl(usuario,dniFrente,dniReverso,imagenSosteniendoDni);
        }catch (Exception e){
            throw new Exception("Error al subir los archivos "+ e.getMessage());
        }
    }

    private void asignarUrl(Usuario usuario, String dniFrente, String dniReverso, String imagenSosteniendoDni) throws IOException {
        Usuario usuarioEncontrado = usuarioRepositorio.findByDni(usuario.getDni());
        asignarUrlImagen(usuario, dniFrente, usuarioEncontrado::setImagenDniFrente);
        asignarUrlImagen(usuario, dniReverso, usuarioEncontrado::setImagenDniReverso);
        asignarUrlImagen(usuario, imagenSosteniendoDni, usuarioEncontrado::setImagenSosteniendoDni);
        usuarioRepositorio.save(usuarioEncontrado);
    }

    private void asignarUrlImagen(Usuario usuario, String imagenNombre, Consumer<String> setter) throws IOException {
        String nombreImagen = gestorDeArchivosServicio.generarNombreImagen(usuario, imagenNombre);
        ArchivoS3Imagen archivo = gestorDeArchivosServicio.obtenerArchivoS3PorNombre("nursenear", nombreImagen);
        if (archivo != null) {
            setter.accept(archivo.getUrl());
        }
    }

    private void guardarUsuario(Usuario usuario) throws Exception {
        verificarQueNoExistaElUsuario(usuario);
        usuarioRepositorio.save(usuario);
    }

}
