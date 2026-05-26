
package com.gatepass.services;

import com.gatepass.data.models.Resident;
import com.gatepass.data.repositories.ResidentRepository;
import com.gatepass.dtos.request.OnboardResidentRequest;
import com.gatepass.dtos.responses.OnboardResidentResponse;
import com.gatepass.exceptions.ResidentAlreadyRegisteredException;
import com.gatepass.exceptions.ResidentDoesNotExistException;
import com.gatepass.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResidentManagementService {

    @Autowired
    private ResidentRepository residentRepository;

    public OnboardResidentResponse onboardResident(OnboardResidentRequest request) {

        Resident existingByPhone = residentRepository.findByPhoneNumber(request.getPhoneNumber());
        if (existingByPhone != null)
            throw new ResidentAlreadyRegisteredException("A resident with this phone number already exists");

        Resident existingByEmail = residentRepository.findByEmail(request.getEmail());
        if (existingByEmail != null)
            throw new ResidentAlreadyRegisteredException("A resident with this email already exists");

        Resident resident = Mapper.map(request);
        residentRepository.save(resident);

        return Mapper.map(resident);
    }

    public String deleteResident(String phoneNumber) {
        Resident resident = residentRepository.findByPhoneNumber(phoneNumber);

        if (resident == null)
            throw new ResidentDoesNotExistException("Resident not available");

        residentRepository.delete(resident);
        return "Resident deleted successfully";
    }

    public List<Resident> viewResidents() {
        return residentRepository.findAll();
    }

    public String disableResident(String phoneNumber) {
        Resident resident = residentRepository.findByPhoneNumber(phoneNumber);

        if (resident == null)
            throw new ResidentDoesNotExistException("Resident unavailable");

        resident.setEnabled(false);
        residentRepository.save(resident);

        return resident.getName() + " disabled";
    }
}