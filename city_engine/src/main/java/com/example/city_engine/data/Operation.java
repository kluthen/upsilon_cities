package com.example.city_engine.data;
import java.time.LocalDateTime;

import lombok.Data;


@Data
public class Operation {
    public enum Status {
        Running,
        Paused,
        Stopped;
    }

    private Long id;
    private String name;
    private ItemStack[] inputs = new ItemStack[]{};
    private ItemStack[] outputs = new ItemStack[]{};
    private int productionDelay = 10; //in s
    private Operation.Status status = Operation.Status.Stopped;
    private LocalDateTime lastStart = LocalDateTime.MIN;
    private LocalDateTime nextEnd = LocalDateTime.MIN;

    public void start()
    {
        lastStart = LocalDateTime.now();
        nextEnd = lastStart.plusSeconds(productionDelay);
        status = Operation.Status.Running;
    }

    public void pause()
    {
        lastStart = LocalDateTime.MIN;
        nextEnd = LocalDateTime.MIN;
        status = Operation.Status.Paused;
    }

    public void stop()
    {
        lastStart = LocalDateTime.MIN;
        nextEnd = LocalDateTime.MIN;
        status = Operation.Status.Stopped;
    }


    public static Operation Create() {
        var op = new Operation();
        op.setName("TestOperation");
        op.setProductionDelay(20);
        op.setInputs(new ItemStack[]{new ItemStack("TestItemInput", 5)});
        op.setOutputs(new ItemStack[]{new ItemStack("TestItemOutput", 2)});
        return op;
    }
    public static Operation CreateGenerator() {
        var op = new Operation();
        op.setName("TestOperation");
        op.setOutputs(new ItemStack[]{new ItemStack("TestItemInput", 10)});
        return op;
    }
}
