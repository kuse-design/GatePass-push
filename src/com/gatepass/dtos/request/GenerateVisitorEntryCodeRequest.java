package com.gatepass.dtos.request;

import lombok.Data;

@Data

public class GenerateVisitorEntryCodeRequest {

    private String residentId;
    private String visitorphoneNumber;
    private String purposeOfVisit;
    private String visitorName;



}
