package com.cuatrodecopas.salud.core.entidades;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "profesional_habilidad3")
public class ProfesionalHabilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idprofesional")
    @JsonIgnore
    private Profesional profesional;

    @ManyToOne
    @JoinColumn(name = "idhabilidad")
    private Habilidad habilidad;

    @Column(name = "precio_profesional")
    private Double precioProfesional;


}