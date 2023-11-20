package com.mssendgrid.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mssendgrid.test.DTO.DataInfoDTO;
import com.mssendgrid.test.DTO.TempaltesDTO;
import com.mssendgrid.test.Tasks.MessageAutoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SendQueueController {
    private final String URL_GET_TEMPLATE_NAME = "";

    @Autowired
    private MessageAutoTask messageAutoTask;
    private RestTemplate restTemplate;

    private String getNameTemplate(String template) {
        TempaltesDTO nameTemplate = restTemplate.getForObject(template, TempaltesDTO.class);
        return nameTemplate.getNameTemplate();
    }

    @PostMapping(path = "/sendQueue")
    public ResponseEntity<Object> sendQueueInfo(@RequestBody DataInfoDTO dataQueue) throws JsonProcessingException {
        dataQueue.setTemplate(getNameTemplate(URL_GET_TEMPLATE_NAME));
        messageAutoTask.sendMessage(dataQueue);
        return new ResponseEntity<>(
                dataQueue,
                HttpStatus.ACCEPTED
        );
    }


}
