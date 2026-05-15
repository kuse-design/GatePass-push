package com.gatepass.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse {

    private final String message;
    private final boolean success;
    private  Object data;


}
