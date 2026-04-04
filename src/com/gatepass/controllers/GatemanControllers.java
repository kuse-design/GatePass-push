package com.gatepass.controllers;

import com.gatepass.dtos.request.ValidateCodeRequest;
import com.gatepass.dtos.responses.*;
import com.gatepass.services.GateAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gateman")
public class GatemanControllers {

    @Autowired
    private GateAccessService gateAccessService;

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse> validate(@RequestBody ValidateCodeRequest request) {
        try {
            ValidateCodeResponse response = gateAccessService.validateCode(request);
            return ResponseEntity.ok(
                    new ApiResponse("Gate pass valid", true, response)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse( e.getMessage(),false, null));
        }
    }
}