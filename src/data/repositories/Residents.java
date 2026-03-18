package data.repositories;

import data.models.Resident;
import java.util.ArrayList;
import java.util.List;

public class Residents implements ResidentRepository {
    private List<Resident> residents = new ArrayList<>();
    private int  nextId = 1;

    @Override
    public List<Resident> findAll() {
        return new ArrayList<>(residents);
    }

    @Override
    public Resident findById(String id) {
        for (Resident resident : residents) {
            if (resident.getId().equals(id)) {
                return resident;
            }
        }
        return null;
    }

   @Override
   public Resident findByPhoneNumber(String phoneNumber) {
        for (Resident resident : residents) {
            if (resident.getPhoneNumber().equals(phoneNumber)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public Resident save(Resident resident) {

        if (findById(resident.getId()) == null) {
            resident.setId(String.valueOf(nextId++));
            residents.add(resident);
        }

        return resident;
    }
    @Override
    public void delete(Resident resident) {
        residents.remove(resident);
    }


    @Override
    public void deleteById(String id) {
        Resident resident = findById(String.valueOf(id));
        if (resident != null) {
            residents.remove(resident);
        }
    }

    @Override
    public void deleteAll() {
        residents.clear();
    }

    public int count() {
        return residents.size();
    }
}