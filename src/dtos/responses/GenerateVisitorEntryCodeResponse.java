package dtos.responses;

public class GenerateVisitorEntryCodeResponse {

    private String code;
    private String visitorName;
    private String validTill;
    private String codeType;
    private String Message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getMessage() {
        return  Message;
    }
    public void setMessage(String message) {
        this.Message = message;
    }
}


