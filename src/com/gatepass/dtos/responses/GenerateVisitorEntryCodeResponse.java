package com.gatepass.dtos.responses;

import lombok.Data;

@Data
public class GenerateVisitorEntryCodeResponse {

    private String code;
    private String visitorName;
    private String validTill;
    private String codeType;
    private String Message;


}


