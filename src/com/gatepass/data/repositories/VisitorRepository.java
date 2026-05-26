package com.gatepass.data.repositories;

import com.gatepass.data.models.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends MongoRepository<Visitor, String> {

    Visitor findByPhoneNumber(String phoneNumber);
}
