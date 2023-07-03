package com.cuatrodecopas.salud.delivery.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioLoginDto {
    @NotBlank
    @Email
    private String mail;


    @Size(min = 8)
    @NotBlank
    private String password;

}
