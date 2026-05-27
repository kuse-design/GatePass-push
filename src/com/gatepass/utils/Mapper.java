package com.gatepass.utils;

import com.gatepass.data.models.GatePass;
import com.gatepass.data.models.Resident;
import com.gatepass.data.models.Types;
import com.gatepass.data.models.Visitor;
import com.gatepass.dtos.request.*;
import com.gatepass.dtos.responses.*;

public class Mapper {

    public static Resident map(OnboardResidentRequest request) {
        Resident resident = new Resident();
        resident.setName(request.getName());
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setHouseAddress(request.getAddress());
        resident.setEmail(request.getEmail());

        return resident;
    }

    public static OnboardResidentResponse map(Resident resident) {
        OnboardResidentResponse response = new OnboardResidentResponse();
        response.setResidentId(resident.getId());
        response.setResidentCode(resident.getResidentCode());
        response.setResidentName(resident.getName());
        response.setPhoneNumber(resident.getPhoneNumber());
        response.setHouseAddress(resident.getHouseAddress());
        response.setDateRegistered(
                resident.getDateRegistered() != null
                        ? resident.getDateRegistered().toString()
                        : null
        );
        return response;
    }

    public static GatePass mapResidentGatePass(GenerateResidentEntryCodeRequest request, Resident resident) {
        GatePass gatePass = new GatePass();
        gatePass.setResidentId(resident.getId());
        gatePass.setPassType(Types.RESIDENT_ENTRY);
        gatePass.setExpiresAt(request.getValidTill());
        return gatePass;
    }

    public static GenerateResidentEntryCodeResponse map(GatePass gatePass, Resident resident) {
        GenerateResidentEntryCodeResponse response = new GenerateResidentEntryCodeResponse();
        response.setCode(gatePass.getCode());
        response.setResidentName(resident.getName());
        response.setCodeType(gatePass.getPassType().name());
        response.setValidTill(
                gatePass.getExpiresAt() != null ? gatePass.getExpiresAt().toString() : "No expiry"
        );
        return response;
    }

    public static Visitor mapVisitor(GenerateVisitorEntryCodeRequest request, String residentId) {
        Visitor visitor = new Visitor();
        visitor.setName(request.getVisitorName());
        visitor.setPhoneNumber(request.getVisitorPhoneNumber());
        visitor.setPurposeOfVisiting(request.getPurposeOfVisit());
        visitor.setResidentId(residentId);
        return visitor;
    }

    public static GatePass mapVisitorGatePass(GenerateVisitorEntryCodeRequest request, String visitorId, Resident resident) {
        GatePass gatePass = new GatePass();
        gatePass.setResidentId(resident.getId());
        gatePass.setVisitorId(visitorId);
        gatePass.setPassType(Types.VISITOR_ENTRY);
        return gatePass;
    }

    public static GenerateVisitorEntryCodeResponse mapToVisitorResponse(
            GatePass gatePass, GenerateVisitorEntryCodeRequest request) {
        GenerateVisitorEntryCodeResponse response = new GenerateVisitorEntryCodeResponse();
        response.setCode(gatePass.getCode());
        response.setVisitorName(request.getVisitorName());
        response.setCodeType(gatePass.getPassType().name());
        response.setValidTill(
                gatePass.getExpiresAt() != null ? gatePass.getExpiresAt().toString() : "No expiry"
        );
        return response;
    }

    public static GenerateExitCodeResponse mapToExitResponse(GatePass gatePass) {
        GenerateExitCodeResponse response = new GenerateExitCodeResponse();
        response.setCode(gatePass.getCode());
        response.setPassType(gatePass.getPassType().name());
        response.setValidTill(
                gatePass.getExpiresAt() != null ? gatePass.getExpiresAt().toString() : "No expiry"
        );
        return response;
    }
}