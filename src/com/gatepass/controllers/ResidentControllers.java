package com.gatepass.controllers;

import com.gatepass.dtos.request.OnboardResidentRequest;
import com.gatepass.dtos.responses.ApiResponse;
import com.gatepass.services.ResidentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/residents")
@CrossOrigin(origins = "*")
public class ResidentControllers {

    @Autowired
    private ResidentManagementService residentManagementService;

    @PostMapping("/onboard")
    public ResponseEntity<ApiResponse> onboardResident(@RequestBody OnboardResidentRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Resident onboarded successfully", true,
                            residentManagementService.onboardResident(request)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllResidents() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Residents fetched", true,
                            residentManagementService.viewResidents()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @DeleteMapping("/{phoneNumber}")
    public ResponseEntity<ApiResponse> deleteResident(@PathVariable String phoneNumber) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(residentManagementService.deleteResident(phoneNumber), true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PatchMapping("/{phoneNumber}/disable")
    public ResponseEntity<ApiResponse> disableResident(@PathVariable String phoneNumber) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(residentManagementService.disableResident(phoneNumber), true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }
}