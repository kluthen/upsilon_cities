package com.example.city_engine.data.dto;

import com.example.city_engine.data.City;
import com.example.city_engine.data.Storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class CityMapper {

    static public class CityDTO extends DTO    {}

    @Data 
    @EqualsAndHashCode(callSuper=false)
    @AllArgsConstructor
    static public class SimpleDTO extends CityDTO{
        private String name;
        private int level;
        private int size;
    }
    
    @Data 
    @EqualsAndHashCode(callSuper=false)
    @AllArgsConstructor
    static public class WithStorageDTO  extends  CityDTO{
        private String name;
        private int level;
        private int size;
        private Storage storage;
    }
    @Data
    @EqualsAndHashCode(callSuper=false) 
    @AllArgsConstructor
    static public class CompleteForUserDTO  extends  CityDTO{
        private String name;
        private int level;
        private int size;
        private FactoryMapper.SimpleDTO[] factories = new FactoryMapper.SimpleDTO[]{};
        private Storage storage;
    }

    static public SimpleDTO toSimple(City c) {
        var r = new SimpleDTO(c.getName(), c.getLevel(), c.getSize());
        r.setId(c.getId());
        return r;
    }
    static public WithStorageDTO toWithStorage(City c, Long userid) {
        var r = new WithStorageDTO(c.getName(), c.getLevel(), c.getSize(), c.getUserStorages().get(userid));
        r.setId(c.getId());
        return r;
    }
    static public CompleteForUserDTO toCompleteForUserDTO(City c, Long userid) {
        var r = new CompleteForUserDTO(c.getName(), c.getLevel(), c.getSize(), FactoryMapper.forUser(c.getFactories(), userid), c.getUserStorages().get(userid));
        r.setId(c.getId());
        return r;
    }
}
