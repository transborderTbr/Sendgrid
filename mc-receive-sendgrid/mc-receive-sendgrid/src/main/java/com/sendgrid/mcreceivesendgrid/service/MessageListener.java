package com.sendgrid.mcreceivesendgrid.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.mcreceivesendgrid.dto.ReceiveActiveMq;
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
      //  tomar el nombre y consumir api
        String url = "http://localhost:8082/plantilla/"+receiveActiveMq.getTemplate();
        restTemplate = new RestTemplate();

   //     ReceivePlantilla receivePlantilla = restTemplate.getForObject(url, ReceivePlantilla.class);
//        receiveActiveMq = ReceiveActiveMq.builder()
//                .cc(Arrays.asList("jhongrisrod@gmail.com","rangel1998.rt@gmail.com"))
//                .subject("este es un subject")
//                .build();
//        ReceivePlantilla receivePlantilla = ReceivePlantilla.builder()
//                .content("html")
//                .build();
       // URL urlPlantilla = Main.class.getResource("src/main/resources/plantilla.html");
        File file = ResourceUtils.getFile("classpath:templates/plantilla.html");
        InputStream isPlantilla = new FileInputStream(file);
        String html = new BufferedReader(new InputStreamReader(isPlantilla))
                .lines().collect(Collectors.joining("\n"));
        html = html.replace("@Name", "angel");
        //if (receivePlantilla!=null ){
        //enviar notificacion
        emailService.sendBulkEmails(receiveActiveMq,html);
     //   } else {
      //      throw new RuntimeException("no se pudo obtener la plantilla");
       // }
    }


}
