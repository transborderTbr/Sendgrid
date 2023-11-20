package com.mssendgrid.test.Tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mssendgrid.test.DTO.DataInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableScheduling
@Service
public class MessageAutoTask {

    private final String to = "to";
    private final String subject = "subject";
    private final String cc = "cc";
    private final String params = "params";
    private final String template = "template";
    private final String QUEUE = "queue.sendgrid.transborder";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(DataInfoDTO data) throws JsonProcessingException {
        String content = dataInfoFormatter( data );
        jmsTemplate.convertAndSend( QUEUE, content );
    }

    private String dataInfoFormatter(DataInfoDTO data) throws JsonProcessingException {

        ObjectMapper obj = new ObjectMapper();

        HashMap<String, Object> dataObject = new HashMap<>();
        dataObject.put(to, data.getTo());
        dataObject.put(subject, data.getSubject());
        dataObject.put(cc, data.getCc());
        dataObject.put(params, data.getParams());
        dataObject.put(template, data.getTemplate());

        return obj.writeValueAsString(dataObject);
    }
}
