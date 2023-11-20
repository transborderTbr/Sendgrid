package com.mssendgrid.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mssendgrid.test.DTO.DataInfoDTO;
import com.mssendgrid.test.DTO.TempaltesDTO;
import com.mssendgrid.test.Tasks.MessageAutoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SendQueueController {

//    @Value("${urlGetTemplates}")
    private String URL_GET_TEMPLATE_NAME = "http://localhost:8082/plantilla";

    @Autowired
    private MessageAutoTask messageAutoTask;

    private String getNameTemplate(String template) {
        RestTemplate restTemplate = new RestTemplate();
        TempaltesDTO[] nameTemplate = restTemplate.getForObject(template, TempaltesDTO[].class);
        String nameTemplateFinal = "";
        for ( TempaltesDTO name: nameTemplate) {
            nameTemplateFinal = name.getNombre();
        }
        return nameTemplateFinal;
    }

    @PostMapping(path = "/sendQueue")
    public ResponseEntity<Object> sendQueueInfo(@RequestBody DataInfoDTO dataQueue) throws JsonProcessingException {
        /*dataQueue.setTemplate(getNameTemplate(URL_GET_TEMPLATE_NAME));*/
        messageAutoTask.sendMessage(dataQueue);
        return new ResponseEntity<>(
                dataQueue,
                HttpStatus.ACCEPTED
        );
    }


}
