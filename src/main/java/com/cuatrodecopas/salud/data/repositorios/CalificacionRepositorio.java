package com.cuatrodecopas.salud.data.repositorios;
import com.cuatrodecopas.salud.core.entidades.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepositorio  extends JpaRepository<Calificacion, Long> {
    Calificacion findCalificaionById(Long id);
    @Query("SELECT c.puntajeDeUsuarioAProfesional FROM Calificacion c JOIN c.prestacion p WHERE c.prestacion.profesional.id = :profesionalId")
    List<Double> listaDeCalificacionesDeProfesional(@Param("profesionalId") Long profesionalId);

    @Query("SELECT c.puntajeDeProfesionalAUsuario FROM Calificacion c JOIN c.prestacion p WHERE c.prestacion.usuario.id = :usuarioId")
    List<Double> listaDeCalificacionesDeUsuario(@Param("usuarioId") Long usuarioId);

    Calificacion findByPrestacionId(Long id);
}
