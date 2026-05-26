package com.gatepass.data.repositories;

import com.gatepass.data.models.EstateManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateManagerRepository extends MongoRepository<EstateManager, String> {

    EstateManager findByEmail(String email);
    EstateManager findByPhoneNumber(String phoneNumber);
}
