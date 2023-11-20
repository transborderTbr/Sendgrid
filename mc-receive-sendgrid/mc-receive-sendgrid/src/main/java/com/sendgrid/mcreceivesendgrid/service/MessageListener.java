package com.sendgrid.mcreceivesendgrid.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.mcreceivesendgrid.dto.ReceiveActiveMq;
import com.sendgrid.mcreceivesendgrid.dto.ReceivePlantilla;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MessageListener {
    ObjectMapper objectMapper=new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();

    ReceiveActiveMq receiveActiveMq;
    @Autowired
    EmailService emailService;
    @JmsListener(destination = "queue.sendgrid.transborder")
    public void receiveMessaqe(String message) throws IOException {
        log.info("mensaje recibido: " + message);
        //leer activeMQ
        receiveActiveMq= objectMapper.readValue(message, ReceiveActiveMq.class);
        String url = "http://localhost:8082/plantilla/"+receiveActiveMq.getTemplate();
        restTemplate = new RestTemplate();
        ReceivePlantilla receivePlantilla = restTemplate.getForObject(url, ReceivePlantilla.class);
        String ruta = "classpath:templates" + receivePlantilla.getUrl();
        File file = ResourceUtils.getFile(ruta);
        InputStream isPlantilla = new FileInputStream(file);
        String html = new BufferedReader(new InputStreamReader(isPlantilla))
                .lines().collect(Collectors.joining("\n"));
        html = html.replace("@name", receiveActiveMq.getParams().getName());
        emailService.sendBulkEmails(receiveActiveMq,html);
    }


}
