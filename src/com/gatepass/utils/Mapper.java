package com.gatepass.utils;

import com.gatepass.data.models.GatePass;
import com.gatepass.data.models.Resident;
import com.gatepass.data.models.Types;
import com.gatepass.dtos.request.*;
import com.gatepass.dtos.responses.*;

import java.time.LocalDateTime;

public class Mapper {


    public static Resident map(OnboardResidentRequest request) {
        Resident resident = new Resident();

        resident.setName(request.getName());
        resident.setPhoneNumber(request.getPhoneNumber());
        resident.setHouseAddress(request.getAddress());

        return resident;
    }

    public static OnboardResidentResponse map(Resident resident) {
        OnboardResidentResponse response = new OnboardResidentResponse();

        response.setResidentId(resident.getId());
        response.setResidentName(resident.getName());
        response.setPhoneNumber(resident.getPhoneNumber());
        response.setHouseAddress(resident.getHouseAddress());

        return response;
    }

    public static GatePass map(GenerateResidentEntryCodeRequest request) {
        GatePass gatePass = new GatePass();

        gatePass.setResidentId(Integer.parseInt(request.getResidentId())); // ⚠️ convert String → int
        gatePass.setPassType(Types.ENTRY);
        gatePass.setExpiresAt(request.getValidTill().atDate(LocalDateTime.now().toLocalDate()));

        return gatePass;
    }

    public static GenerateResidentEntryCodeResponse map(GatePass gatePass, Resident resident) {
        GenerateResidentEntryCodeResponse response = new GenerateResidentEntryCodeResponse();

        response.setCode(gatePass.getCode());
        response.setResidentName(resident.getName());
        response.setCodeType(Types.ENTRY.name());
        response.setValidTill(gatePass.getExpiresAt().toString());

        return response;
    }

    public static GatePass map(GenerateVisitorEntryCodeRequest request) {
        GatePass gatePass = new GatePass();

        gatePass.setResidentId(Integer.parseInt(request.getResidentId()));
        gatePass.setPassType(Types.ENTRY);

        return gatePass;
    }

    public static GenerateVisitorEntryCodeResponse mapVisitor(GatePass gatePass, GenerateVisitorEntryCodeRequest request) {
        GenerateVisitorEntryCodeResponse response = new GenerateVisitorEntryCodeResponse();

        response.setCode(gatePass.getCode());
        response.setVisitorName(request.getVisitorName());
        response.setCodeType(Types.ENTRY.name());

        return response;
    }

    public static void map(GenerateExitCodeRequest request, GatePass gatePass) {

        gatePass.setPassType(Types.EXIT);
        gatePass.setExpiresAt(request.getValidTill());
    }

    public static GenerateExitCodeResponse mapToExitResponse(GatePass gatePass) {
        GenerateExitCodeResponse response = new GenerateExitCodeResponse();

        response.setCode(gatePass.getCode());
        response.setPassType(gatePass.getPassType().name());
        response.setValidTill(gatePass.getExpiresAt().toString());

        return response;
    }
}