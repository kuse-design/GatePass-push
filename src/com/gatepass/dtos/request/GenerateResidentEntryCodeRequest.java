package com.gatepass.dtos.request;

import lombok.Data;

import java.time.LocalTime;

@Data
public class GenerateResidentEntryCodeRequest {

    private String residentId;
    private LocalTime validTill;
    private String phoneNumber;




}

