package utils;

import data.models.Resident;
import dtos.request.OnboardResidentRequest;
import dtos.responses.OnboardResidentResponse;

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
}