package com.example.city_engine.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.city_engine.data.City;

@Repository
public interface CityRepository  extends JpaRepository<City, Long> {
    
}
