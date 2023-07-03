package com.cuatrodecopas.salud.data.repositorios;

import com.cuatrodecopas.salud.core.entidades.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenunciaRepositorio extends JpaRepository<Denuncia, Long> {

    Denuncia findDenunciaById(Long idDenuncia);

    List<Denuncia> findByUsuarioId(Long idUsuario);
}
