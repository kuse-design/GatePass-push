package data.repositories;

import data.models.GatePass;
import java.util.List;

public interface GatePassRepository {
    List<GatePass> findAll();
    GatePass findById(int id);
    GatePass findByCode(String code);
    GatePass save(GatePass pass);
    void delete(GatePass pass);
    void deleteById(int id);
    void deleteAll();
}

