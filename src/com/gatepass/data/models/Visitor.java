package com.gatepass.data.models;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Visitor {


    private String id;
    private String name;
    private String purposeOfVisiting;
    private String phoneNumber;
    private String residentId;


}
