package com.cuatrodecopas.salud.core.entidades;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "direccion")
public class Direccion {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "calle")
        private String calle;

        @Column(name = "localidad")
        private String localidad;

        @Column(name = "ciudad")
        private String ciudad;

        @Column(name = "latitud")
        private String latitud;

        @Column(name = "longitud")
        private String longitud;

        @Column(name = "numero")
        private Integer numero;

}
