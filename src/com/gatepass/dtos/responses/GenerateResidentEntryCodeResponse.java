package com.gatepass.dtos.responses;

import lombok.Data;

@Data
public class GenerateResidentEntryCodeResponse {

    private String code;
    private String residentName;
    private String codeType;
    private String validTill;
    private String destination;
    private String Message;

}

