package com.gatepass.services;

import com.gatepass.data.models.EstateManager;
import com.gatepass.data.models.GatePass;
import com.gatepass.data.models.Resident;
import com.gatepass.data.models.Visitor;
import com.gatepass.data.repositories.*;
import com.gatepass.dtos.request.EstateManagerRequest;
import com.gatepass.dtos.responses.EstateManagerResponse;
import com.gatepass.exceptions.InvalidGatePassException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstateManagerService {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private GatePassRepository gatePassRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private EstateManagerRepository estateManagerRepository;


    public EstateManagerResponse addManager(EstateManagerRequest request) {
        EstateManager existing = estateManagerRepository.findByEmail(request.getEmail());

        if (existing != null)
            throw new RuntimeException("A manager with this email already exists");

        EstateManager manager = new EstateManager();
        manager.setName(request.getName());
        manager.setEmail(request.getEmail());manager.setEmail(request.getEmail());
        manager.setPhoneNumber(request.getPhoneNumber());
        manager.setPassword(request.getPassword());

        EstateManager saved = estateManagerRepository.save(manager);
        return mapToResponse(saved);
    }

    public String removeManager(String managerId) {
        EstateManager manager = estateManagerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        estateManagerRepository.delete(manager);
        return manager.getName() + " removed successfully";
    }

    public String disableManager(String managerId) {
        EstateManager manager = estateManagerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        manager.setActive(false);
        estateManagerRepository.save(manager);
        return manager.getName() + " disabled";
    }

    public List<EstateManager> getAllManagers() {
        return estateManagerRepository.findAll();
    }

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public List<GatePass> getAllGatePasses() {
        return gatePassRepository.findAll();
    }

    public List<GatePass> getGatePassesByResident(String residentId) {
        return gatePassRepository.findByResidentId(residentId);
    }

    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    public List<Visitor> getVisitorsByResident(String residentId) {
        return visitorRepository.findByResidentId(residentId);
    }


    public GatePass approveCode(String code) {
        GatePass gatePass = gatePassRepository.findByCode(code);

        if (gatePass == null)
            throw new InvalidGatePassException("Gate pass not found");

        gatePass.setActive(true);
        gatePass.setValid(true);
        return gatePassRepository.save(gatePass);
    }

    public GatePass revokeCode(String code) {
        GatePass gatePass = gatePassRepository.findByCode(code);

        if (gatePass == null)
            throw new InvalidGatePassException("Gate pass not found");

        gatePass.setActive(false);
        gatePass.setValid(false);
        return gatePassRepository.save(gatePass);
    }

    private EstateManagerResponse mapToResponse(EstateManager manager) {
        EstateManagerResponse response = new EstateManagerResponse();
        response.setId(manager.getId());
        response.setName(manager.getName());
        response.setEmail(manager.getEmail());
        response.setPhoneNumber(manager.getPhoneNumber());
        response.setActive(manager.isActive());
        return response;
    }
}
