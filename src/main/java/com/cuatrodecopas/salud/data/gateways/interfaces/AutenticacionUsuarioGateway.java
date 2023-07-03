package com.cuatrodecopas.salud.data.gateways.interfaces;

import com.cuatrodecopas.salud.core.entidades.Usuario;

public interface AutenticacionUsuarioGateway {
    void registrarUsuario(Usuario usuario);
    String loginUsuario(String mail, String password);
}
