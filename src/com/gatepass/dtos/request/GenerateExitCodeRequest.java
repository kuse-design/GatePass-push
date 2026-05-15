package com.gatepass.dtos.request;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document
@Data
public class GenerateExitCodeRequest {

    private String code;
    private LocalDateTime validTill;


}