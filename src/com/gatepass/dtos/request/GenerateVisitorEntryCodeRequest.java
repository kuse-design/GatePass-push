package com.gatepass.dtos.request;

import lombok.Data;

@Data

public class GenerateVisitorEntryCodeRequest {

    private String residentId;
    private String visitorphoneNumber;
    private String purposeOfVisit;
    private String visitorName;

//    public String getResidentId() {
//        return residentId;
//    }
//
//    public void setResidentId(String residentId) {
//        this.residentId = residentId;
//    }
//
//    public String getVisitorPhone() {
//        return visitorPhone;
//    }
//
//    public void setVisitorPhone(String visitorPhone) {
//        this.visitorPhone = visitorPhone;
//    }
//
//    public String getPurposeOfVisit() {
//        return purposeOfVisit;
//    }
//
//    public void setPurposeOfVisit(String purposeOfVisit) {
//        this.purposeOfVisit = purposeOfVisit;
//    }
//
//    public String getVisitorName() {
//        return visitorName;
//    }
//
//    public void setVisitorName(String visitorName) {
//        this.visitorName = visitorName;
//    }
//

}
