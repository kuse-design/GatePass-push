package com.gatepass.controllers;

import com.gatepass.dtos.request.EstateManagerRequest;
import com.gatepass.dtos.request.EstateManagerRequest;
import com.gatepass.dtos.responses.ApiResponse;
import com.gatepass.services.EstateManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager")
@CrossOrigin(origins = "*")
public class EstateManagerController {

    @Autowired
    private EstateManagerService estateManagerService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addManager(@RequestBody EstateManagerRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Manager added successfully", true,
                            estateManagerService.addManager(request)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllManagers() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Managers fetched", true,
                            estateManagerService.getAllManagers()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @DeleteMapping("/{managerId}")
    public ResponseEntity<ApiResponse> removeManager(@PathVariable String managerId) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(estateManagerService.removeManager(managerId), true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PatchMapping("/{managerId}/disable")
    public ResponseEntity<ApiResponse> disableManager(@PathVariable String managerId) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(estateManagerService.disableManager(managerId), true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @GetMapping("/residents")
    public ResponseEntity<ApiResponse> getAllResidents() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("All residents fetched", true,
                            estateManagerService.getAllResidents()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @GetMapping("/gate-passes")
    public ResponseEntity<ApiResponse> getAllGatePasses() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("All gate passes fetched", true,
                            estateManagerService.getAllGatePasses()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @GetMapping("/gate-passes/resident/{residentId}")
    public ResponseEntity<ApiResponse> getGatePassesByResident(@PathVariable String residentId) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Gate passes fetched for resident", true,
                            estateManagerService.getGatePassesByResident(residentId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @GetMapping("/visitors")
    public ResponseEntity<ApiResponse> getAllVisitors() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("All visitors fetched", true,
                            estateManagerService.getAllVisitors()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @GetMapping("/visitors/resident/{residentId}")
    public ResponseEntity<ApiResponse> getVisitorsByResident(@PathVariable String residentId) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Visitors fetched for resident", true,
                            estateManagerService.getVisitorsByResident(residentId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PatchMapping("/gate-passes/{code}/approve")
    public ResponseEntity<ApiResponse> approveCode(@PathVariable String code) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Code manually approved", true,
                            estateManagerService.approveCode(code)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PatchMapping("/gate-passes/{code}/revoke")
    public ResponseEntity<ApiResponse> revokeCode(@PathVariable String code) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("Code revoked", true,
                            estateManagerService.revokeCode(code)));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(e.getMessage(), false, null));
        }
    }
}


