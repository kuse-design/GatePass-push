package services;

import data.models.GatePass;
import data.models.Types;
import data.repositories.GatePassRepository;
import data.repositories.ResidentRepository;
import dtos.request.GenerateResidentEntryCodeRequest;
import dtos.request.GenerateVisitorEntryCodeRequest;
import dtos.request.ValidateCodeRequest;
import dtos.responses.GenerateResidentEntryCodeResponse;
import dtos.responses.GenerateVisitorEntryCodeResponse;
import dtos.responses.ValidateCodeResponse;
import utils.RandomCodeGenerator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GateAccessService {

    private ResidentRepository residentRepository;
    private GatePassRepository gatePassRepository;

    public GateAccessService(ResidentRepository residentRepository,
                             GatePassRepository gatePassRepository) {
        this.residentRepository = residentRepository;
        this.gatePassRepository = gatePassRepository;
    }

    private GatePass createGatePass(String code, Types type) {
        GatePass gatePass = new GatePass();
        gatePass.setCode(code);
        gatePass.setPassType(type);
        return gatePassRepository.save(gatePass);
    }

    public String generateExitCode(String code) {
        String exitCode = RandomCodeGenerator.generateCode(gatePassRepository);
        createGatePass(exitCode, Types.EXIT);
        return exitCode;
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