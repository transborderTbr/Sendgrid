package com.SendPlantillas.Plantillas.Controller;

import com.SendPlantillas.Plantillas.ControllerImpl.PlantillaControllerImpl;
import com.SendPlantillas.Plantillas.DTO.DataPlantillaDTO;
import com.SendPlantillas.Plantillas.DTO.PlantillaDto;
import com.SendPlantillas.Plantillas.Entity.PlantillaEntity;
import com.SendPlantillas.Plantillas.Repository.PlantillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlantillaController implements PlantillaControllerImpl {

    @Autowired
    PlantillaRepository plantillaRepository;

    @Override
    public List<PlantillaDto> getPlantillas(){
        List<PlantillaEntity> entities = plantillaRepository.findAll();
        return MapEntityToDtoList(entities);
    }

    @Override
    public PlantillaDto getPlantillaByName(String name, DataPlantillaDTO dataPlantillaDTO) {
        PlantillaEntity entities = plantillaRepository.findByNombre(name);
        return MapEntityToDto(entities,dataPlantillaDTO);
    }

    @Override
    public List<PlantillaDto> save(PlantillaDto plantillaDto){
        plantillaRepository.save(MapDtoToEntity(Long.valueOf(0),plantillaDto));
        List<PlantillaEntity> entities = plantillaRepository.findAll();
        return MapEntityToDtoList(entities);
    }

    private List<PlantillaDto> MapEntityToDtoList(List<PlantillaEntity> plantillaEntity){
        List<PlantillaDto> plantillaDto = new ArrayList<>();
        for (int i = 0; i < plantillaEntity.size(); i++){
            PlantillaDto dto = new PlantillaDto();
            dto.setId(plantillaEntity.get(i).getId());
            dto.setNombre(plantillaEntity.get(i).getNombre());
            dto.setContenido(plantillaEntity.get(i).getContenido());
            plantillaDto.add(dto);
        }
        return plantillaDto;
    }

    private PlantillaDto MapEntityToDto(PlantillaEntity plantillaEntity,DataPlantillaDTO dataPlantillaDTO) {
        PlantillaDto dto = new PlantillaDto();
        dto.setId(plantillaEntity.getId());
        dto.setNombre(plantillaEntity.getNombre());
        dto.setContenido(LeerPlantilla(plantillaEntity.getContenido(),dataPlantillaDTO));
        return dto;
    }

    private PlantillaEntity MapDtoToEntity(Long id,PlantillaDto paisDto){
        PlantillaEntity paisEntity = new PlantillaEntity();
        if (id==0){
            paisEntity.setNombre(paisDto.getNombre());
            paisEntity.setContenido(paisDto.getContenido());
        }else{
            paisEntity.setId(id);
            paisEntity.setNombre(paisDto.getNombre());
            paisEntity.setContenido(paisDto.getContenido());
        }
        return paisEntity;
    }

    private String LeerPlantilla(String contenidoBD,DataPlantillaDTO dataPlantillaDTO){
        return contenidoBD.replace("@Name",dataPlantillaDTO.getName());
    }

}
