package com.cuatrodecopas.salud.data.repositorios;

import com.cuatrodecopas.salud.core.entidades.Habilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HabilidadRepositorio extends JpaRepository<Habilidad, Long> {
    Habilidad findHabilidadById(Long idHabilidad);

}
