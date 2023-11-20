package com.sendgrid.mcreceivesendgrid.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.mcreceivesendgrid.dto.ReceiveActiveMq;
import com.sendgrid.mcreceivesendgrid.dto.ReceivePlantilla;
import com.sendgrid.mcreceivesendgrid.dto.SendPlantilla;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class MessageListener {
    ObjectMapper objectMapper=new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();

    ReceiveActiveMq receiveActiveMq;
    @Autowired
    EmailService emailService;
    @JmsListener(destination = "queue.sendgrid.transborder")
    public void receiveMessaqe(String message) throws JsonProcessingException {
        log.info("mensaje recibido: " + message);
        //leer activeMQ
        receiveActiveMq= objectMapper.readValue(message, ReceiveActiveMq.class);
      //  tomar el nombre y consumir api
//        String url = "http://localhost:8083/api/v1/plantilla";
//        restTemplate = new RestTemplate();
//        SendPlantilla sendPlantilla = SendPlantilla.builder()
//                .plantilla(receiveActiveMq.getTemplate())
//                .params(receiveActiveMq.getParams())
//                .build();
////
//        ResponseEntity<ReceivePlantilla> receivePlantilla = restTemplate.postForEntity(url, sendPlantilla, ReceivePlantilla.class);
//        receiveActiveMq = ReceiveActiveMq.builder()
//                .cc(Arrays.asList("jhongrisrod@gmail.com","rangel1998.rt@gmail.com"))
//                .subject("este es un subject")
//                .build();
        ReceivePlantilla receivePlantilla = ReceivePlantilla.builder()
                .content("html")
                .build();
        if (receivePlantilla!=null ){
        //enviar notificacion
        emailService.sendBulkEmails(receiveActiveMq,receivePlantilla.getContent());
        } else {
            throw new RuntimeException("no se pudo obtener la plantilla");
        }
    }

}
