package com.gatepass.dtos.responses;

import lombok.Data;

@Data
public class EstateManagerResponse {

    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private boolean active;
}
