package com.gatepass.data.repositories;

import com.gatepass.data.models.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends MongoRepository<Visitor, String> {

    Visitor findByPhoneNumber(String phoneNumber);
    List<Visitor> findByResidentId(String residentId);
}
