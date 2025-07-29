package com.example.city_engine.data;

import java.util.HashMap;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name="cities")
@Data public class City {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int level = 1;
    private int size = 10;  

    @OneToMany(mappedBy = "city")
    private Factory[] factories = new Factory[]{};

    @Transient
    private HashMap<Long, Storage> userStorages = new HashMap<>();

    // Generate a Test city with one test Factory.
    public static City Create()
    {
        var city = new City();
        city.setFactories(new Factory[]{ Factory.Create()});
        return city;
    }

}
