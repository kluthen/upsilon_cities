package com.example.city_engine.data;
import lombok.Data;

@Data
public class Factory {
    private String name;
    private int level = 1;
    private Operation[] operations;
    private Storage storage = new Storage();

    // Create one basic test factory with a basic test operation.
    public static Factory Create() 
    {
        var factory = new Factory();
        factory.setName("Factory");
        factory.setOperations(new Operation[]{Operation.CreateGenerator(), Operation.Create()});
        return factory;
    }
}


