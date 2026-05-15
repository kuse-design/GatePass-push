package com.gatepass.data.repositories;
import com.gatepass.data.models.Resident;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResidentRepository extends MongoRepository<Resident, String> {

    Resident findByPhoneNumber(String phoneNumber);



}