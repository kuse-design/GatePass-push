package com.gatepass.data.repositories;

import com.gatepass.data.models.GatePass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GatePassRepository extends MongoRepository<GatePass, String> {

    GatePass findByCode(String code);

    List<GatePass> findByResidentId(String residentId);
}

