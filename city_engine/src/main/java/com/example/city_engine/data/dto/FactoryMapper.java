package com.example.city_engine.data.dto;

import java.util.ArrayList;

import com.example.city_engine.data.Factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class FactoryMapper {
    
    static public class FactoryDTO extends DTO{}

    @Data
    @EqualsAndHashCode(callSuper=false) 
    @AllArgsConstructor
    static public class SimpleDTO extends FactoryDTO
    {
        private String name;
        private int level;
    }

    @Data
    @EqualsAndHashCode(callSuper=false) 
    @AllArgsConstructor
    static public class WithSimplfiedOperationDTO extends FactoryDTO
    {
        private String name;
        private int level;
        private OperationMapper.SimpleDTO[] operations;
    }

    static public SimpleDTO toSimple(Factory factory) 
    {   
        var r = new SimpleDTO(factory.getName(), factory.getLevel());
        r.setId(factory.getId());
        return r;
    }

    static public SimpleDTO[] forUser(Factory[] factories, Long user_id) 
    {
        ArrayList<SimpleDTO> res = new ArrayList<>();

        for(Factory f: factories)
        {
            if(f.getUserId().equals(user_id))
                res.add(toSimple(f));
        }

        return (SimpleDTO[])res.toArray();
    }

    static public WithSimplfiedOperationDTO toWithSimplfiedOperationDTO(Factory factory) 
    {   
        var r = new WithSimplfiedOperationDTO(factory.getName(), factory.getLevel(), OperationMapper.toSimple(factory.getOperations()));
        r.setId(factory.getId());
        return r;
    }

    static public WithSimplfiedOperationDTO[] forUserWithSimplfiedOperationDTO(Factory[] factories, Long user_id) 
    {
        ArrayList<WithSimplfiedOperationDTO> res = new ArrayList<>();

        for(Factory f: factories)
        {
            if(f.getUserId().equals(user_id))
                res.add(toWithSimplfiedOperationDTO(f));
        }

        return (WithSimplfiedOperationDTO[])res.toArray();
    }
}
