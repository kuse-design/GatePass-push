package com.gatepass.dtos.responses;

import lombok.Data;

@Data
public class OnboardResidentResponse {

    private String residentId;
    private String dateRegistered;
    private String residentName;
    private String houseAddress;
    private String phoneNumber;

//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getHouseAddress() {
//        return houseAddress;
//    }
//
//    public void setHouseAddress(String houseAddress) {
//        this.houseAddress = houseAddress;
//    }
//
//    public String getResidentId() {
//        return residentId;
//    }
//
//    public void setResidentId(String residentId) {
//        this.residentId = residentId;
//    }
//
//    public String getDateRegistered() {
//        return dateRegistered;
//    }
//
//    public void setDateRegistered(String dateRegistered) {
//        this.dateRegistered = dateRegistered;
//    }
//
//    public String getResidentName() {
//        return residentName;
//    }
//
//    public void setResidentName(String residentName) {
//        this.residentName = residentName;
//    }
}
