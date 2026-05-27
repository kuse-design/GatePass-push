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
        if (request.getValidTill() != null) gatePass.setExpiresAt(request.getValidTill());
        gatePassRepository.save(gatePass);
        return Mapper.mapToExitResponse(gatePass);
    }

    public GenerateResidentEntryCodeResponse generateResidentEntryCode(
            GenerateResidentEntryCodeRequest request) {

        Resident resident = residentRepository.findByResidentCode(request.getResidentCode());
        if (resident == null)
            throw new ResidentDoesNotExistException("Resident not found for code: " + request.getResidentCode());

        GatePass gatePass = Mapper.mapResidentGatePass(request, resident);
        gatePass.setCode(RandomCodeGenerator.generateCode(gatePassRepository));

        GatePass saved = gatePassRepository.save(gatePass);
        return Mapper.map(saved, resident);
    }

    public GenerateVisitorEntryCodeResponse generateVisitorEntryCode(
            GenerateVisitorEntryCodeRequest request) {

        Resident resident = residentRepository.findByResidentCode(request.getResidentCode());
        if (resident == null)
            throw new ResidentDoesNotExistException("Resident not found for code: " + request.getResidentCode());

        Visitor visitor = Mapper.mapVisitor(request, resident.getId());
        Visitor savedVisitor = visitorRepository.save(visitor);

        GatePass gatePass = Mapper.mapVisitorGatePass(request, savedVisitor.getId(), resident);
        gatePass.setCode(RandomCodeGenerator.generateCode(gatePassRepository));

        GatePass saved = gatePassRepository.save(gatePass);
        return Mapper.mapToVisitorResponse(saved, request);
    }

    public String disableCode(String code) {
        GatePass gatePass = gatePassRepository.findByCode(code);
        if (gatePass == null)
            throw new InvalidGatePassException("Code not found");
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

        response.setValid(true);
        response.setCodeType(gatePass.getPassType().name());

        switch (gatePass.getPassType()) {
            case RESIDENT_ENTRY -> {
                residentRepository.findById(gatePass.getResidentId()).ifPresent(resident -> {
                    response.setResidentName(resident.getName());
                    // FIX: also return readable code in validate response
                    response.setCreatedBy(resident.getResidentCode());
                });
                response.setMessage("Resident entry allowed");
            }
            case VISITOR_ENTRY -> {
                residentRepository.findById(gatePass.getResidentId()).ifPresent(resident ->
                        response.setCreatedBy(resident.getName() + " (" + resident.getResidentCode() + ")")
                );
                visitorRepository.findById(gatePass.getVisitorId()).ifPresent(visitor ->
                        response.setVisitorsName(visitor.getName())
                );
                response.setMessage("Visitor entry allowed");
            }
            case EXIT -> response.setMessage("Exit allowed");
        }

        return response;
    }

    public void extendTime(String code, String newExpiryTime) {
        GatePass pass = gatePassRepository.findByCode(code);
        if (pass == null)
            throw new InvalidGatePassException("Gate pass not found");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        pass.setExpiresAt(LocalDateTime.parse(newExpiryTime, formatter));
        gatePassRepository.save(pass);
    }
}