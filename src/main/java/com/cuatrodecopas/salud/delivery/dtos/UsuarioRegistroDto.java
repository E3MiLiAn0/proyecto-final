package com.cuatrodecopas.salud.delivery.dtos;

import com.cuatrodecopas.salud.core.entidades.Usuario;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroDto {

    private MultipartFile imagenDniFrente;

    private MultipartFile imagenDniReverso;

    private MultipartFile imagenSosteniendoDni;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank @Email
    private String mail;

    @NotBlank
    private String dni;

    @Size(min = 8)
    @NotBlank
    private String password;
    public Usuario UsuarioRegistroDtoAUsuario(String passwordEncoder){
        return Usuario.builder().nombre(this.nombre)
                        .apellido(this.apellido)
                        .mail(this.mail)
                        .dni(this.dni)
                        .password(passwordEncoder)
                        .imagenDniFrente(this.imagenDniFrente.getOriginalFilename())
                        .imagenDniReverso(this.imagenDniReverso.getOriginalFilename())
                        .imagenSosteniendoDni(this.imagenSosteniendoDni.getOriginalFilename())
                        .build();
    }

}
