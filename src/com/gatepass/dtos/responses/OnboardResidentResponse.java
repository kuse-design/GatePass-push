package com.gatepass.dtos.responses;

import lombok.Data;

@Data
public class OnboardResidentResponse {

    private String residentId;
    private String residentCode;
    private String dateRegistered;
    private String residentName;
    private String houseAddress;
    private String phoneNumber;


}
