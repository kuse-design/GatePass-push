package com.gatepass.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class EstateManager {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean active = true;
}
