package com.example.city_engine.data;

import lombok.Getter;

@Getter
public class ImmutableItemStack {
    private final String name;
    private final int count;
    private final boolean is_null;

    public ImmutableItemStack(ItemStack it)
    {
        name = it.getName();
        count = it.getCount();
        is_null = false;
    }
    public ImmutableItemStack()
    {
        name = "nullItem";
        is_null = true;
        count = 0;
    }

    public boolean isNull() {
        return is_null;
    }
}