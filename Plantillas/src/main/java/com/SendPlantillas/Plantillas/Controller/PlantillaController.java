package com.SendPlantillas.Plantillas.Controller;

import com.SendPlantillas.Plantillas.ControllerImpl.PlantillaControllerImpl;
import com.SendPlantillas.Plantillas.DTO.PlantillaDto;
import com.SendPlantillas.Plantillas.Entity.PlantillaEntity;
import com.SendPlantillas.Plantillas.Repository.PlantillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public PlantillaDto getPlantillaByName(String name) {
        PlantillaEntity entities = plantillaRepository.findByNombre(name);
        return MapEntityToDto(entities);
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
            dto.setUrl(plantillaEntity.get(i).getUrl());
            plantillaDto.add(dto);
        }
        return plantillaDto;
    }

    private PlantillaDto MapEntityToDto(PlantillaEntity plantillaEntity) {
        PlantillaDto dto = new PlantillaDto();
        dto.setId(plantillaEntity.getId());
        dto.setNombre(plantillaEntity.getNombre());
        dto.setUrl(plantillaEntity.getUrl());
        return dto;
    }

    private PlantillaEntity MapDtoToEntity(Long id,PlantillaDto paisDto){
        PlantillaEntity paisEntity = new PlantillaEntity();
        if (id==0){
            paisEntity.setNombre(paisDto.getNombre());
            paisEntity.setUrl(paisDto.getUrl());
        }else{
            paisEntity.setId(id);
            paisEntity.setNombre(paisDto.getNombre());
            paisEntity.setUrl(paisDto.getUrl());
        }
        return paisEntity;
    }

}
