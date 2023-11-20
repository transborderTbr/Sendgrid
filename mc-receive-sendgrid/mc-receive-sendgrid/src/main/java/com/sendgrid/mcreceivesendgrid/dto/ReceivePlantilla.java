package com.sendgrid.mcreceivesendgrid.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceivePlantilla {
    private int id;
    private String nombre;
    private String contenido;
}
