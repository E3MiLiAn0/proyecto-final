package com.cuatrodecopas.salud.delivery.dtos;

import com.cuatrodecopas.salud.core.entidades.Direccion;
import com.cuatrodecopas.salud.core.entidades.Profesional;
import com.cuatrodecopas.salud.core.entidades.Usuario;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfesionalEnGuardiaDto {

    private String latitud;
    private String longitud;
    private String dni;
    private Boolean guardiaActiva;

    public Profesional profesionalEnGuardiaDtoAProfesional(ProfesionalEnGuardiaDto profesionalEnGuardiaDto){
        Profesional profesional = new Profesional();
        Direccion direccion = new Direccion();
        Usuario usuario = new Usuario();

        direccion.setLatitud(getLatitud());
        direccion.setLongitud(getLongitud());

        usuario.setDni(getDni());
        usuario.setMail("hola@gmail.com");
        profesional.setDireccionProfesional(direccion);
        profesional.setUsuario(usuario);

        profesional.setGuardiaActiva(profesionalEnGuardiaDto.getGuardiaActiva());


        return profesional;
    }



}
