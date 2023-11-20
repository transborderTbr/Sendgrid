package com.SendPlantillas.Plantillas.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlantillaDto implements Serializable {

    private Long id;
    private String nombre;
    private String contenido;

}
