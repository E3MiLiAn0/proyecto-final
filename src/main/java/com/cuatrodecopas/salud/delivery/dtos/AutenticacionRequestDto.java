package com.cuatrodecopas.salud.delivery.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AutenticacionRequestDto {
    private String mail;
    private String password;
}
