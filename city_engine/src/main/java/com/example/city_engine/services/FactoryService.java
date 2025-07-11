package com.example.city_engine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.city_engine.data.Factory;
import com.example.city_engine.data.dto.FactoryMapper.FactoryDTO;
import com.example.city_engine.repositories.FactoryRepository;

@Service
public class FactoryService {
    
    @Autowired
    private FactoryRepository repo;

    public Factory fromDTO(FactoryDTO d)
    {
        return repo.fromId(d.getId());
    }
}
