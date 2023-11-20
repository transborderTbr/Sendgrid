package com.SendPlantillas.Plantillas.ControllerImpl;

import com.SendPlantillas.Plantillas.DTO.PlantillaDto;

import java.util.List;

public interface PlantillaControllerImpl {

    List<PlantillaDto> getPlantillas();

    PlantillaDto getPlantillaByName(String name);

    List<PlantillaDto> save(PlantillaDto plantillaDto);
}
