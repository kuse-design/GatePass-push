package com.gatepass.dtos.request;

import lombok.Data;

@Data
public class OnboardResidentRequest {

    private String phoneNumber;
    private  String email;
    private String name;
    private String address;

}
