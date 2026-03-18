package data.models;

public class Visitor {

    private int id;
    private String name;
    private String purposeOfVisiting;
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurposeOfVisiting() {
        return purposeOfVisiting;
    }

    public void setPurposeOfVisiting(String purposeOfVisiting) {
        this.purposeOfVisiting = purposeOfVisiting;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
