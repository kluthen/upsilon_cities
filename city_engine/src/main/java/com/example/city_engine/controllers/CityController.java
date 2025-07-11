package com.example.city_engine.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.city_engine.data.dto.CityMapper;



@RestController
public class CityController {

    @GetMapping("/cities")
    public CityMapper.SimpleDTO[] getCities() {
        
    }

    @GetMapping("/cities/{id}")
    public String getMethodName(@PathVariable String name) {
        

        return new String();
    }
    
}
