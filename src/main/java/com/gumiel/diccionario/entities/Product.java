package com.gumiel.diccionario.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
@Schema( name = "Entity Product (Producto)")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "El identificador")
  private Integer id;
  @Column(length = 30, nullable = false, name = "code")
  @Schema(description = "El codigo")
  private String code;
  @Column(length = 100, nullable = false, name = "name")
  @Schema(description = "El nombre")
  private String name;
  @Column(length = 500, nullable = false, name = "description")
  @Schema(description = "La descripcion")
  private String description;
  @Column(name = "unit_measurement")
  @Schema(description = "Identificador de unidad de medida")
  private int unitMeasurement;
  @Column(name = "approximate_unit_price")
  @Schema(description = "Campo del precio aproximado")
  private BigDecimal approximateUnitPrice;
}
