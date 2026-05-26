package com.gatepass.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document
@Data
public class Resident {

        private String id;
        private String name;
        private String phoneNumber;
        private String houseAddress;
        private String email;
        private LocalDateTime dateRegistered = LocalDateTime.now();
        private boolean enabled = true;

}
