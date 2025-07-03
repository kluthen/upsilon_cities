package com.example.city_engine.data;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data 
public class ItemStack {
    private String name;
    private int count = 0;

    public ItemStack updateCount(int c)
    {
        count = count + c;
        return this;
    }
}


