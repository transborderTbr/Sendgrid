package com.SendPlantillas.Plantillas.ControllerImpl;

import com.SendPlantillas.Plantillas.DTO.DataPlantillaDTO;
import com.SendPlantillas.Plantillas.DTO.PlantillaDto;

import java.io.IOException;
import java.util.List;

public interface PlantillaControllerImpl {

    List<PlantillaDto> getPlantillas();

    PlantillaDto getPlantillaByName(String name, DataPlantillaDTO dataPlantillaDTO);

    List<PlantillaDto> save(PlantillaDto plantillaDto);
}
