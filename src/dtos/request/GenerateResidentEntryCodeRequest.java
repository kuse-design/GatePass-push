package dtos.request;

import java.time.LocalTime;

public class GenerateResidentEntryCodeRequest {

    private String residentId;
    private LocalTime validTill;

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public LocalTime getValidTill() {
        return validTill;
    }

    public void setValidTill(LocalTime validTill) {
        this.validTill = validTill;
    }



}

