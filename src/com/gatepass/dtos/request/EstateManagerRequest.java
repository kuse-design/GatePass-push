package com.gatepass.dtos.request;

import lombok.Data;

@Data
public class EstateManagerRequest {

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
}
