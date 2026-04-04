package com.gatepass.dtos.responses;

import lombok.Data;

@Data
public class GenerateExitCodeResponse {

    private String code;
    private String passType;
    private String validTill;
}