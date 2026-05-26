package com.gatepass.dtos.request;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class GenerateResidentEntryCodeRequest {

    private String residentId;
    private LocalDateTime validTill;
    private String phoneNumber;




}

