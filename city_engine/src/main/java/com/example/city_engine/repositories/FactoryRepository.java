package com.example.city_engine.repositories;
import org.springframework.stereotype.Repository;

import com.example.city_engine.data.Factory;


@Repository
public class FactoryRepository {
    
    public Factory fromId(Long id)
    {
        // TODO: implement.
        return new Factory();
    }
}
