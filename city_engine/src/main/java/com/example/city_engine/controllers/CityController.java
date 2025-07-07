package com.example.city_engine.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class CityController {

    @GetMapping("/cities")
    public String getCities() {
        return new String();
    }

    @GetMapping("/city/{id}")
    public String getMethodName(@PathVariable String name) {
        

        return new String();
    }
    
}
