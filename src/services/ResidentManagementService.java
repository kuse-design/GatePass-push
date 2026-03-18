package services;

import data.models.Resident;
import data.repositories.ResidentRepository;
import data.repositories.Residents;
import dtos.request.OnboardResidentRequest;
import dtos.responses.OnboardResidentResponse;
import exceptions.ResidentAlreadyRegisteredException;
import exceptions.ResidentDoesNotExistException;
import utils.Mapper;

import java.util.List;

public class ResidentManagementService {

    private ResidentRepository residentRepository = new Residents();

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