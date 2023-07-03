package com.cuatrodecopas.salud.delivery.dtos;

import com.cuatrodecopas.salud.core.entidades.Direccion;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValidarUsuariooDto {

    private String dni;
    private MultipartFile selfie;
    private Direccion direccion;



}
