package com.gatepass.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document
@Data
public class GatePass {

    private String id;
    private String code;
    private String residentId;
    private Types passType;
    private String visitorId;
    private LocalDateTime createdAt =  LocalDateTime.now();
    private LocalDateTime expiresAt;
    private boolean valid = true;
    private boolean active = true;

}
