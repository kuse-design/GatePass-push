package com.gatepass.services;

import com.gatepass.data.models.Resident;
import com.gatepass.data.repositories.ResidentRepository;
import com.gatepass.dtos.request.OnboardResidentRequest;
import com.gatepass.dtos.responses.OnboardResidentResponse;
import com.gatepass.exceptions.ResidentAlreadyRegisteredException;
import com.gatepass.exceptions.ResidentDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gatepass.utils.Mapper;
import java.util.List;

@Service
public class ResidentManagementService {

    @Autowired
    private ResidentRepository residentRepository;

    public OnboardResidentResponse onboardResident(OnboardResidentRequest request){

        Resident existingResident = residentRepository.findByPhoneNumber(request.getPhoneNumber());

        validateCheckDuplicateFor(existingResident);
        Resident resident = Mapper.map(request);
        residentRepository.save(resident);

        return Mapper.map(resident);
    }

    public String deleteResident(String phoneNumber){

        Resident resident =
                residentRepository.findByPhoneNumber(phoneNumber);

        if(resident == null)
            throw new ResidentDoesNotExistException("Resident not available");
        residentRepository.delete(resident);
        return "Resident deleted successfully";
    }

    public List<Resident> viewResident(){
        return residentRepository.findAll();
    }

    private void validateCheckDuplicateFor(Resident resident){
        if(resident != null){
            throw new ResidentAlreadyRegisteredException("User already exists");
        }
    }

    public String disableResident(String phoneNumber){

        Resident resident =
                residentRepository.findByPhoneNumber(phoneNumber);

        if(resident == null)
            throw new ResidentDoesNotExistException("Resident unavailable");

        resident.setEnabled(false);

        return resident.getName() + " disabled";
    }
}