package com.cuatrodecopas.salud.data.repositorios;

import com.cuatrodecopas.salud.core.entidades.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, Long> {
    @Query("SELECT p FROM Profesional p WHERE p.usuario.id = :id")
    Profesional validarProfesionalConMatriculaActiva(@Param("id") Long id);
    Profesional findByUsuarioDniAndEstadoDeLaMatriculaIsTrue(String dni);
    List<Profesional> findByGuardiaActivaIsTrueAndDisponibleIsTrue();
    //Profesional findByUsuarioDniAndDisponibleIsTrueAndGuardiaActivaIsTrue(String dni);

    @Query("SELECT p FROM Profesional p JOIN Usuario u On p.usuario.id = u.id WHERE u.dni = :dni and p.disponible = true and p.guardiaActiva = true")
    Profesional profesionalPorDniUsuario(String dni);

    List<Profesional> findByGuardiaActivaIsTrueAndAndDisponibleIsTrueAndDireccionProfesional_Localidad(String localidad);
    Profesional findProfesionalById(Long id);
    Profesional findByUsuarioDni(String dni);
}



