package com.sendgrid.mcreceivesendgrid.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.sendgrid.mcreceivesendgrid.dto.ReceiveActiveMq;
import com.sendgrid.mcreceivesendgrid.dto.ReceivePlantilla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private SendGrid sendGrid;
    @Value("${twilio.sendgrid.from-email}")
    private String fromEmail;

    public void sendSingleEmail(String toEmail) {
        // specify the email details
        Email from = new Email(this.fromEmail);
        String subject = "Hello, World!";
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", "Welcome to the Twilio SendGrid world!");

        // initialize the Mail helper class
        Mail mail = new Mail(from, subject, to, content);

        // send the single email
        sendEmail(mail);
    }
    public void sendBulkEmails(ReceiveActiveMq receiveActiveMq, ReceivePlantilla plantilla) {
        // specify the email details
        Mail mail = new Mail();
        mail.setFrom(new Email(this.fromEmail));
        mail.setSubject(receiveActiveMq.getSubject());
        mail.addContent(new Content("text/html", plantilla.getContenido()));

        // add the multiple recipients to the email
        Personalization personalization = new Personalization();
//        String data = receiveActiveMq.getCc().replace("[","").replace("]","");
//        List<String> myList = new ArrayList<>(Arrays.asList(data.split(",")));
        receiveActiveMq.getCc().forEach(cc -> {
            // add each destination email address to the BCC
            // field of the email
            personalization.addBcc(new Email(cc));
            personalization.addTo(new Email(receiveActiveMq.getTo()));
            personalization.setSubject(receiveActiveMq.getSubject());
        });
        mail.addPersonalization(personalization);

        // send the bulk email
        sendEmail(mail);
    }
    private void sendEmail(Mail mail) {
        try {
            // set the SendGrid API endpoint details as described
            // in the doc (https://docs.sendgrid.com/api-reference/mail-send/mail-send)
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            // perform the request and send the email
            Response response = sendGrid.api(request);
            int statusCode = response.getStatusCode();
            // if the status code is not 2xx
            if (statusCode < 200 || statusCode >= 300) {
                throw new RuntimeException(response.getBody());
            }
        } catch (IOException e) {
            // log the error message in case of network failures
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
