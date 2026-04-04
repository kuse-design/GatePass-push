package com.gatepass.dtos.request;

import lombok.Data;

import java.time.LocalTime;

@Data
public class GenerateResidentEntryCodeRequest {

    private String residentId;
    private LocalTime validTill;
    private String phoneNumber;

//    public String getResidentId() {
//        return residentId;
//    }
//
//    public void setResidentId(String residentId) {
//        this.residentId = residentId;
//    }
//
//    public LocalTime getValidTill() {
//        return validTill;
//    }
//
//    public void setValidTill(LocalTime validTill) {
//        this.validTill = validTill;
//    }



}

