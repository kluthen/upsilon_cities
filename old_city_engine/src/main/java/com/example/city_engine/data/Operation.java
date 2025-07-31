package com.example.city_engine.data;
import java.time.LocalDateTime;

import com.example.city_engine.data.converter.ItemStackAttributeConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Operation {
    public enum Status {
        Running,
        Paused,
        Stopped;
    }

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name="factory_id")
    private Factory factory;

    @Convert(converter = ItemStackAttributeConverter.class)
    private ItemStack[] inputs = new ItemStack[]{};
    @Convert(converter = ItemStackAttributeConverter.class)
    private ItemStack[] outputs = new ItemStack[]{};
    
    private int productionDelay = 10000; //in ms

    @Enumerated(EnumType.STRING)
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
