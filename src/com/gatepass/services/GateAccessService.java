package com.gatepass.services;

import com.gatepass.data.models.GatePass;
import com.gatepass.data.models.Types;
import com.gatepass.data.repositories.GatePassRepository;
import com.gatepass.data.repositories.ResidentRepository;
import com.gatepass.dtos.request.GenerateExitCodeRequest;
import com.gatepass.dtos.request.GenerateResidentEntryCodeRequest;
import com.gatepass.dtos.request.GenerateVisitorEntryCodeRequest;
import com.gatepass.dtos.request.ValidateCodeRequest;
import com.gatepass.dtos.responses.GenerateExitCodeResponse;
import com.gatepass.dtos.responses.GenerateResidentEntryCodeResponse;
import com.gatepass.dtos.responses.GenerateVisitorEntryCodeResponse;
import com.gatepass.dtos.responses.ValidateCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gatepass.utils.RandomCodeGenerator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GateAccessService {
    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private GatePassRepository gatePassRepository;

    public GateAccessService(ResidentRepository residentRepository, GatePassRepository gatePassRepository) {
        this.residentRepository = residentRepository;
        this.gatePassRepository = gatePassRepository;
    }

    private GatePass createGatePass(String code, Types type) {
        GatePass gatePass = new GatePass();
        gatePass.setCode(code);
        gatePass.setPassType(type);
        return gatePassRepository.save(gatePass);
    }

    public GenerateExitCodeResponse generateExitCode(GenerateExitCodeRequest request) {

        String exitCode = RandomCodeGenerator.generateCode(gatePassRepository);

        createGatePass(exitCode, Types.EXIT);

        GenerateExitCodeResponse response = new GenerateExitCodeResponse();
        response.setCode(exitCode);

        return response;
    }

    public GenerateResidentEntryCodeResponse generateResidentEntryCode(
            GenerateResidentEntryCodeRequest request) {

        String code = RandomCodeGenerator.generateCode(gatePassRepository);
        createGatePass(code, Types.ENTRY);
        GenerateResidentEntryCodeResponse response = new GenerateResidentEntryCodeResponse();

        response.setCode(code);
        response.setMessage("Resident entry code generated");

        return response;
    }

    public GenerateVisitorEntryCodeResponse generateVisitorEntryCode(
            GenerateVisitorEntryCodeRequest request) {

        String code = RandomCodeGenerator.generateCode(gatePassRepository);
        createGatePass(code, Types.ENTRY);
        GenerateVisitorEntryCodeResponse response = new GenerateVisitorEntryCodeResponse();

        response.setCode(code);
        response.setMessage("Visitor entry code generated");

        return response;
    }

    public String disableCode(String code) {
        GatePass gatePass = gatePassRepository.findByCode(code);

        if (gatePass == null) {
            return "Code not found";
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
        }
        else if (!gatePass.isActive()) {
            response.setValid(false);
            response.setMessage("Code is disabled");
        }
        else if (gatePass.getPassType() == Types.ENTRY) {
            response.setValid(true);
            response.setMessage("Entry allowed");
        }
        else {
            response.setValid(true);
            response.setMessage("Exit allowed");
        }
        return response;
    }

    public void extendTime(String code, String newExpiryTime) {
        GatePass pass = gatePassRepository.findByCode(code);
        if (pass == null) throw new RuntimeException("Gate pass not found");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        pass.setExpiresAt(LocalDateTime.parse(newExpiryTime, formatter));
        gatePassRepository.save(pass);
    }

}