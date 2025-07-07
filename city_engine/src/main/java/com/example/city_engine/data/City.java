package com.example.city_engine.data;

import java.util.HashMap;

import lombok.Data;

@Data public class City {
    private Long id;
    private String name;
    private int level = 1;
    private int size = 10;  
    private Factory[] factories = new Factory[]{};
    private HashMap<Long, Storage> userStorages = new HashMap<>();

    // Generate a Test city with one test Factory.
    public static City Create()
    {
        var city = new City();
        city.setFactories(new Factory[]{ Factory.Create()});
        return city;
    }

}
