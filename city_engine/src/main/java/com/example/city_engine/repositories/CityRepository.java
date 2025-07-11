package com.example.city_engine.repositories;
import org.springframework.stereotype.Repository;

import com.example.city_engine.data.City;

@Repository
public class CityRepository {
    
    public City fromId(Long id)
    {
        // TODO: implement.
        return new City();
    }
}
