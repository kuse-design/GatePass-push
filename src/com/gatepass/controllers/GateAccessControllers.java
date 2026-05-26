package com.gatepass.controllers;

import com.gatepass.dtos.request.*;
import com.gatepass.dtos.responses.ApiResponse;
import com.gatepass.services.GateAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gate")
@CrossOrigin(origins = "*")
public class GateAccessControllers {

    @Autowired
    private GateAccessService gateAccessService;

    @PostMapping("/exit-code")
    public ResponseEntity<ApiResponse> generateExitCode(@RequestBody GenerateExitCodeRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Exit code generated", true,
                            gateAccessService.generateExitCode(request)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PostMapping("/resident-entry-code")
    public ResponseEntity<ApiResponse> generateResidentEntryCode(
            @RequestBody GenerateResidentEntryCodeRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Resident entry code generated", true,
                            gateAccessService.generateResidentEntryCode(request)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PostMapping("/visitor-entry-code")
    public ResponseEntity<ApiResponse> generateVisitorEntryCode(
            @RequestBody GenerateVisitorEntryCodeRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Visitor entry code generated", true,
                            gateAccessService.generateVisitorEntryCode(request)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse> validateCode(@RequestBody ValidateCodeRequest request) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Code validated", true,
                            gateAccessService.validateCode(request)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PatchMapping("/{code}/disable")
    public ResponseEntity<ApiResponse> disableCode(@PathVariable String code) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(gateAccessService.disableCode(code), true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PatchMapping("/{code}/extend")
    public ResponseEntity<ApiResponse> extendCode(
            @PathVariable String code,
            @RequestParam String newExpiryTime) {
        try {
            gateAccessService.extendTime(code, newExpiryTime);
            return ResponseEntity.ok(
                    new ApiResponse("Code expiry extended", true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }
}