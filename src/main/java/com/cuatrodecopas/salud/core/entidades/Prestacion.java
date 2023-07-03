package com.cuatrodecopas.salud.core.entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "prestacion")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Prestacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descripcion;

    @Column(columnDefinition = "boolean DEFAULT false")
    private Boolean estadoActiva;// una vez aceptada y mientras el profesional este brindando el servicio  la prestacion esta activa es decir true

    @Column(columnDefinition = "boolean DEFAULT true")
    private Boolean pendienteDeRespuesta;// mientras el profesional no acepte ninguna esto es false, una vez que acepto esta u otra pasa a true
                                        // estado activa de la solitud aceptada pasa a false

    @Column(columnDefinition = "boolean DEFAULT false")
    private Boolean prestacionAceptada;

    @Column
    private Date fechaHoraInicio;

    @Column
    private Date fechaHoraFinalizacion;

    @Column
    private Date fechaCreada;

    @Column
    private Boolean pagada;

    @Column(name = "precio_total")
    private Double precioTotal;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_prestacion_profesional")
    private Double precioPrestacionProfesional;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id")
    private Direccion direccionPrestacion;

    @OneToOne(mappedBy = "prestacion")
    private Calificacion calificacion;

    @OneToOne
    @JoinColumn(name ="habilidad_id")
    private Habilidad habilidad;
}
