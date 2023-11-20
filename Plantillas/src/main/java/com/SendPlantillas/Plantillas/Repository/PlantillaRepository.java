package com.SendPlantillas.Plantillas.Repository;

import com.SendPlantillas.Plantillas.Entity.PlantillaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantillaRepository extends JpaRepository<PlantillaEntity,Long> {

    @Query(value = "select * from plantilla where nombre LIKE %?1%", nativeQuery = true)
    PlantillaEntity findByNombre(String name);

}
