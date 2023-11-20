package com.SendPlantillas.Plantillas.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="plantilla")
public class PlantillaEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String contenido;

}
