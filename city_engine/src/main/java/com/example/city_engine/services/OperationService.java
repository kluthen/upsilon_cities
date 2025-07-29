package com.example.city_engine.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.city_engine.data.Operation;
import com.example.city_engine.data.dto.OperationMapper.OperationDTO;
import com.example.city_engine.repositories.OperationRepository;

@Service
public class OperationService {
    
    @Autowired
    private OperationRepository repo;

    public Operation fromDTO(OperationDTO d)
    {
        return fromId(d.getId());
    }

    public Operation fromId(Long id)
    {
        // TODO: implement.
        return new Operation();
    }
}
