package com.gatepass.dtos.responses;

import lombok.Data;

@Data
public class ValidateCodeResponse {

    private String residentName;
    private String visitorsName;
    private String codeType;
    private String createdBy;
    private boolean isValid;
    private String Message;


}
