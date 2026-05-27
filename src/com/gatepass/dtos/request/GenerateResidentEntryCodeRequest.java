package com.gatepass.dtos.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GenerateResidentEntryCodeRequest {


    private String residentCode;

    private String phoneNumber;
    private LocalDateTime validTill;
}

