package com.gatepass.dtos.request;

import lombok.Data;

@Data

public class GenerateVisitorEntryCodeRequest {

    private String residentId;
    private String visitorPhoneNumber;
    private String purposeOfVisit;
    private String visitorName;



}
