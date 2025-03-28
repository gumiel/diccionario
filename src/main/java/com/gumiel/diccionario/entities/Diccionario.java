package com.gumiel.diccionario.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "diccionario")
public class Diccionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 500, nullable = false)
    private String tabla;
    @Column(length = 500, nullable = false)
    private String tipoCampo;
    @Column(length = 500, nullable = false)
    private String nombreCampo;
    @Column(length = 500, nullable = false)
    private String descripcionCampo;


}
