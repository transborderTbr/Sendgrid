package com.mssendgrid.test.DTO;

import lombok.Data;

import java.util.HashMap;

@Data
public class DataInfoDTO {
    private String to;
    private String[] cc;
    private String subject;
    private HashMap<String, Object> params;
    private String template;
}
