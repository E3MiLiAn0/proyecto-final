package com.cuatrodecopas.salud.data.repositorios;

import com.cuatrodecopas.salud.core.entidades.Prestacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestacionRepositorio extends JpaRepository<Prestacion, Long> {

    List<Prestacion> findByEstadoActivaIsFalseAndPendienteDeRespuestaIsTrueAndPrestacionAceptadaIsFalseAndProfesionalUsuarioDni(String dni);
    Prestacion findFirstByEstadoActivaIsTrueAndPendienteDeRespuestaIsFalseAndPrestacionAceptadaIsTrueAndProfesionalUsuarioDni(String dni);
    Prestacion findPrestacionById(Long idPrestacion);
    Prestacion findFirstByUsuarioDniOrderByIdDesc(String dni);
    List<Prestacion> findPrestacionByEstadoActivaIsFalseAndPendienteDeRespuestaIsTrueAndPrestacionAceptadaIsFalseAndProfesionalId(Long profesionalId);
    Prestacion findFirstByEstadoActivaIsTrueAndPendienteDeRespuestaIsFalseAndPrestacionAceptadaIsTrueAndUsuarioDni(String dni);
    List<Prestacion> findByProfesionalId(Long id);

    List<Prestacion> findByUsuarioId(Long id);

    @Query("SELECT p FROM Prestacion p WHERE p.profesional.id =:idProfesional AND p.habilidad.id =:idHabilidad AND p.estadoActiva=false and p.pendienteDeRespuesta=false and p.prestacionAceptada=true ")
    List<Prestacion> cantidadDeVecesRealizoUnaHabilidad(@Param("idProfesional") Long idProfesional,
                                                        @Param("idHabilidad") Long idHabilidad);

    @Query("SELECT p FROM Prestacion p WHERE MONTH(p.fechaCreada) =:mes AND p.habilidad.id =:idHabilidad AND p.profesional.id =:idProfesional")
    List<Prestacion> findPrestacionesByFechaAndHabilidadAndProfesional(@Param("idProfesional") Long idProfesional,
                                                                       @Param("idHabilidad") Long idHabilidad,
                                                                       @Param("mes") Integer mes);

    @Query("SELECT p FROM Calificacion c JOIN Prestacion p " +
            "ON p.id = c.prestacion.id" +
            " WHERE p.profesional.id =:profesionalId AND p.habilidad.id = :habilidadId " +
            "ORDER BY c.puntajeDeUsuarioAProfesional DESC")
    List<Prestacion> findByProfesionalYHabilidadOrdenadoPorPuntajeDeProfesional(@Param("profesionalId") Long profesionalId,

                                                                                @Param("habilidadId") Long habilidadId);
    @Query("SELECT p FROM Prestacion p " +
            "WHERE p.profesional.id =:profesionalId " +
            "AND p.estadoActiva = false " +
            "AND p.pendienteDeRespuesta = false " +
            "AND p.prestacionAceptada = true " +
            "ORDER BY p.fechaCreada DESC")
    List<Prestacion> obtenerPrestacionesFinalizadasPorUnProfesional (Long profesionalId);

    @Query("SELECT p FROM Prestacion p " +
            "WHERE p.usuario.id =:usuarioId " +
            "AND p.estadoActiva = false " +
            "AND p.pendienteDeRespuesta = false " +
            "AND p.prestacionAceptada = true " +
            "ORDER BY p.fechaCreada DESC")
    List<Prestacion> obtenerPrestacionesFinalizadasPorUnUsuario(Long usuarioId);


}

