package com.cuatrodecopas.salud.data.repositorios;

import com.cuatrodecopas.salud.core.entidades.Profesional;
import com.cuatrodecopas.salud.core.entidades.ProfesionalHabilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Repository
public interface ProfesionalHabilidadRespositorio extends JpaRepository<ProfesionalHabilidad, Long> {


    @Query("SELECT ph FROM ProfesionalHabilidad ph JOIN Habilidad h ON ph.habilidad.id = h.id WHERE ph.profesional.id=:idProfesional " )
    List<ProfesionalHabilidad> listaDeHabilidadesDeUnProfesional(@Param("idProfesional")Long idProfesional);

    @Query("SELECT p FROM ProfesionalHabilidad ph JOIN Profesional p ON ph.profesional.id = p.id JOIN Direccion d ON p.direccionProfesional.id = d.id WHERE d.localidad  like :localidad AND ph.habilidad.id=:idHabilidad AND p.guardiaActiva=true AND p.disponible=true")
    List<Profesional> ProfesionalesPorLocalidadYHabilidad(@Param("idHabilidad") Long idHabilidad, @Param("localidad") String localidad);


    ProfesionalHabilidad findByProfesionalIdAndHabilidadId(Long idHabilidad, Long idProfesional);
}

