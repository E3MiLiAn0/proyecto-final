package com.cuatrodecopas.salud.data.repositorios;

import com.cuatrodecopas.salud.core.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.dni like :dni")
    Usuario findByDni(@Param("dni") String dni);
    Usuario findByMail(String mail);
    Usuario findUsuarioById(Long idUsuario);
}
