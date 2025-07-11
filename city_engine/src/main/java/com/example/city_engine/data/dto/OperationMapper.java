package com.example.city_engine.data.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.city_engine.data.Operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class OperationMapper {

    static public class OperationDTO extends DTO {}

    @Data
    @EqualsAndHashCode(callSuper=false) 
    @AllArgsConstructor
    static public class SimpleDTO extends OperationDTO
    {
        private String name;
        private Operation.Status status ;
        private LocalDateTime lastStart ;
        private LocalDateTime nextEnd ;    
        private int productionDelay ; //in s
    }
    
    
    static public SimpleDTO toSimple(Operation operation) 
    {   
        var r = new SimpleDTO(
            operation.getName(),
            operation.getStatus(),
            operation.getLastStart(),
            operation.getNextEnd(),
            operation.getProductionDelay()
        );
        r.setId(operation.getId());
        return r;
    }
    
    static public SimpleDTO[] toSimple(Operation[] operations) 
    {   
        ArrayList<SimpleDTO> res = new ArrayList<>();
        for(Operation o: operations)
            res.add(toSimple(o));

        return (SimpleDTO[])res.toArray();
    }

}
