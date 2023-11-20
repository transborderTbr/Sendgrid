package com.SendPlantillas.Plantillas.Service;

import com.SendPlantillas.Plantillas.ControllerImpl.PlantillaControllerImpl;
import com.SendPlantillas.Plantillas.DTO.DataPlantillaDTO;
import com.SendPlantillas.Plantillas.DTO.PlantillaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Service
@RequestMapping(path = "plantilla")
public class PlantillasService {

    @Autowired
    PlantillaControllerImpl plantillaController;

    @GetMapping("")
    public @ResponseBody List<PlantillaDto> getPlantillas() {
        return plantillaController.getPlantillas();
    }

    @PostMapping("/{nombre}")
    public @ResponseBody PlantillaDto getPlantillas(@PathVariable(value = "nombre") String nombre,@RequestBody DataPlantillaDTO dataPlantillaDTO) {
        return plantillaController.getPlantillaByName(nombre,dataPlantillaDTO);
    }

    @PostMapping(consumes = "application/json",produces = "application/json")
    public @ResponseBody List<PlantillaDto> save(@RequestBody PlantillaDto plantillaDto) {
        return plantillaController.save(plantillaDto);
    }

}
