package com.cuatrodecopas.salud.core.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "habilidad")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Habilidad {
    @Id
    private Long id;
    private String descripcion;

    @Column(name = "precio_minimo")
    private Double precioMinimo;

}

