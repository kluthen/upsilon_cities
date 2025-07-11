package com.example.city_engine.repositories;
import com.example.city_engine.data.Operation;
import org.springframework.stereotype.Repository;

@Repository
public class OperationRepository {
    
    public Operation fromId(Long id)
    {
        // TODO: implement.
        return new Operation();
    }
}
