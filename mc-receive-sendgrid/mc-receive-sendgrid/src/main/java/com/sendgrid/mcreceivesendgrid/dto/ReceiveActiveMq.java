package com.sendgrid.mcreceivesendgrid.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiveActiveMq {
    private String to;
    private List<String> cc;
    private String subject;
    private List<Map<String,String>> params;
    private String template;
    private String from;

}
