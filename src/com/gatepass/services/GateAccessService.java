package com.gatepass.services;

import com.gatepass.data.models.GatePass;
import com.gatepass.data.models.Resident;
import com.gatepass.data.models.Types;
import com.gatepass.data.models.Visitor;
import com.gatepass.data.repositories.GatePassRepository;
import com.gatepass.data.repositories.ResidentRepository;
import com.gatepass.data.repositories.VisitorRepository;
import com.gatepass.dtos.request.*;
import com.gatepass.dtos.responses.*;
import com.gatepass.exceptions.InvalidGatePassException;
import com.gatepass.exceptions.ResidentDoesNotExistException;
import com.gatepass.utils.Mapper;
import com.gatepass.utils.RandomCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GateAccessService {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private GatePassRepository gatePassRepository;
    @Autowired
    private VisitorRepository visitorRepository;

    public GateAccessService(ResidentRepository residentRepository,
                             GatePassRepository gatePassRepository,
                             VisitorRepository visitorRepository) {
        this.residentRepository = residentRepository;
        this.gatePassRepository = gatePassRepository;
        this.visitorRepository = visitorRepository;
    }

    public GenerateExitCodeResponse generateExitCode(GenerateExitCodeRequest request) {
        String exitCode = RandomCodeGenerator.generateCode(gatePassRepository);

        GatePass gatePass = new GatePass();
        gatePass.setCode(exitCode);
        gatePass.setPassType(Types.EXIT);

        if (request.getValidTill() != null) {
            gatePass.setExpiresAt(request.getValidTill());
        }

        gatePassRepository.save(gatePass);

        return Mapper.mapToExitResponse(gatePass);
    }

    public GenerateResidentEntryCodeResponse generateResidentEntryCode(
            GenerateResidentEntryCodeRequest request) {

        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new ResidentDoesNotExistException("Resident not found"));

        GatePass gatePass = Mapper.map(request);
        String code = RandomCodeGenerator.generateCode(gatePassRepository);
        gatePass.setCode(code);

        GatePass savedGatePass = gatePassRepository.save(gatePass);

        GenerateResidentEntryCodeResponse response = Mapper.map(savedGatePass, resident);
        response.setMessage("Resident entry code generated");

        return response;
    }

    public GenerateVisitorEntryCodeResponse generateVisitorEntryCode(
            GenerateVisitorEntryCodeRequest request) {

        residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new ResidentDoesNotExistException(
                        "Resident not found for visitor entry"));

        Visitor visitor = Mapper.mapVisitor(request, request.getResidentId());
        Visitor savedVisitor = visitorRepository.save(visitor);

        GatePass gatePass = Mapper.mapVisitorGatePass(request, savedVisitor.getId());
        String code = RandomCodeGenerator.generateCode(gatePassRepository);
        gatePass.setCode(code);

        GatePass savedGatePass = gatePassRepository.save(gatePass);

        GenerateVisitorEntryCodeResponse response =
                Mapper.mapToVisitorResponse(savedGatePass, request);
        response.setMessage("Visitor entry code generated");

        return response;
    }

    public String disableCode(String code) {
        GatePass gatePass = gatePassRepository.findByCode(code);

        if (gatePass == null) {
            throw new InvalidGatePassException("Code not found");

        }


        gatePass.setActive(false);
        gatePassRepository.save(gatePass);

        return "Code disabled";
    }

    public ValidateCodeResponse validateCode(ValidateCodeRequest request) {
        ValidateCodeResponse response = new ValidateCodeResponse();
        GatePass gatePass = gatePassRepository.findByCode(request.getCode());

        if (gatePass == null) {
            response.setValid(false);
            response.setMessage("Invalid code");
            return response;
        }

        if (!gatePass.isActive()) {
            response.setValid(false);
            response.setMessage("Code is disabled");
            return response;
        }

        if (gatePass.getExpiresAt() != null && gatePass.getExpiresAt().isBefore(LocalDateTime.now())) {
            gatePass.setActive(false);
            gatePassRepository.save(gatePass);
            response.setValid(false);
            response.setMessage("Code has expired");
            return response;
        }

        response.setCodeType(gatePass.getPassType().name());
        response.setValid(true);

        if (gatePass.getPassType() == Types.RESIDENT_ENTRY) {
            residentRepository.findById(gatePass.getResidentId()).ifPresent(resident ->
                    response.setResidentName(resident.getName())
            );
            response.setMessage("Resident entry allowed");
        } else if (gatePass.getPassType() == Types.VISITOR_ENTRY) {
            residentRepository.findById(gatePass.getResidentId()).ifPresent(resident ->
                    response.setCreatedBy(resident.getName())
            );
            if (gatePass.getVisitorId() != null) {
                visitorRepository.findById(gatePass.getVisitorId()).ifPresent(visitor ->
                        response.setVisitorsName(visitor.getName())
                );
            }
            response.setMessage("Visitor entry allowed");
        } else {
            response.setMessage("Exit allowed");
        }

        return response;
    }

    public void extendTime(String code, String newExpiryTime) {
        GatePass pass = gatePassRepository.findByCode(code);

        if (pass == null) {
            throw new InvalidGatePassException("Gate pass not found");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        pass.setExpiresAt(LocalDateTime.parse(newExpiryTime, formatter));

        gatePassRepository.save(pass);
    }
}