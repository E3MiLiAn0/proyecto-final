package com.cuatrodecopas.salud.data.repositorios;


import com.cuatrodecopas.salud.core.entidades.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepositorio extends JpaRepository<Direccion, Long> {
}
