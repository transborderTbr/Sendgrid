package com.sendgrid.mcreceivesendgrid.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendPlantilla {
    private String plantilla;
    private Map<String,String> params;
}
