package com.example.city_engine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.city_engine.data.City;
import com.example.city_engine.repositories.CityRepository;
import com.example.city_engine.data.dto.CityMapper.CityDTO;

@Service
public class CityService {
    @Autowired
    private CityRepository repo;

    public City fromDTO(CityDTO d)
    {
        return repo.fromId(d.getId());
    }
}
