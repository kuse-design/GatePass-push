package com.gatepass.controllers;

import com.gatepass.dtos.request.*;
import com.gatepass.dtos.responses.*;
import com.gatepass.services.GateAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.gatepass.dtos.responses.ApiResponse;

@RestController
@RequestMapping("/api/resident")
public class ResidentControllers {

    @Autowired
    private GateAccessService gateAccessService;

    @PostMapping("/entry")
    public ResponseEntity<ApiResponse> generateEntry(@RequestBody GenerateResidentEntryCodeRequest request) {
        try {
            GenerateResidentEntryCodeResponse response = gateAccessService.generateResidentEntryCode(request);
            return new ResponseEntity<>(new ApiResponse("Entry code created", true, response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false, null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/visitor-entry")
    public ResponseEntity<ApiResponse> generateVisitorEntry(@RequestBody GenerateVisitorEntryCodeRequest request) {
        try {
            GenerateVisitorEntryCodeResponse response = gateAccessService.generateVisitorEntryCode(request);
            return new ResponseEntity<>(new ApiResponse("Visitor entry code created", true, response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false, null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/exit")
    public ResponseEntity<ApiResponse> generateExit(@RequestBody GenerateExitCodeRequest request) {
        try {
            GenerateExitCodeResponse response = gateAccessService.generateExitCode(request);
            return new ResponseEntity<>(new ApiResponse("Exit code created", true, response), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false, null), HttpStatus.BAD_REQUEST);
        }
    }
}