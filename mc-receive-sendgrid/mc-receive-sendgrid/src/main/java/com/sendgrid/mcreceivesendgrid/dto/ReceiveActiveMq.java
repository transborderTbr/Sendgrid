package com.sendgrid.mcreceivesendgrid.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveActiveMq {
    private String to;
    private List<String> cc;
    private String subject;
    private Params params;
    private String template;
    private String from;

}
