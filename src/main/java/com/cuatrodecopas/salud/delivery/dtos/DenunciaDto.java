package com.cuatrodecopas.salud.delivery.dtos;

import com.cuatrodecopas.salud.core.entidades.Denuncia;
import com.cuatrodecopas.salud.core.entidades.Usuario;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import static com.cuatrodecopas.salud.configuracion.Configuraciones.obtenerFechaActual;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DenunciaDto {
    @Nullable
    private MultipartFile evidencia;
    private String mensaje;
    private String dniUsuario;

    public Denuncia pasarDenunciaDtoADenuncia(Usuario usuario){
        String nombreArchivo="";

        if(evidencia!=null){
            nombreArchivo=evidencia.getOriginalFilename();
        }

        return Denuncia.builder().mensaje(this.mensaje)
                            .fechaCreada(obtenerFechaActual())
                            .usuario(usuario)
                            .evidencia(nombreArchivo)
                            .build();
    }



}
