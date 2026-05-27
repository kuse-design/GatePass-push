package com.gatepass.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OnboardResidentRequest {

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{11}$",
            message = "Phone number must be 11 digits"
    )
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

}
