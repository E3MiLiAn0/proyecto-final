package com.cuatrodecopas.salud.delivery.dtos;

import com.cuatrodecopas.salud.core.entidades.Habilidad;
import com.cuatrodecopas.salud.core.entidades.Profesional;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InformacionDeUnaHabilidadDeUnProfesionalDto {

    private Profesional profesional;
    private String habilidadDescripcion;
    private Double PrecioQueCobraProfesional;
    @Nullable
    private Integer cantidadDeVecesRealizada;

    public void armarDto(Profesional profesional, String habilidadDescripcion , Double precioQueCobraProfesional, Integer cantidadDeVecesRealizada){
        setProfesional(profesional);
        setHabilidadDescripcion(habilidadDescripcion);
        setPrecioQueCobraProfesional(precioQueCobraProfesional);
        setCantidadDeVecesRealizada(cantidadDeVecesRealizada);
    }

}


