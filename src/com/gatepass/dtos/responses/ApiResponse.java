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

//    public Object getData() {
//        return com.gatepass.data;
//    }
//
//    public void setData(Object com.gatepass.data) {
//        this.com.gatepass.data = com.gatepass.data;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
}
