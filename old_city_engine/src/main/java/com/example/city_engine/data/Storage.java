package com.example.city_engine.data;
import java.util.HashMap;

import lombok.Data;

@Data
public class Storage {
    private int capacity = 100;
    private int usage = 0; //!< cached information.
    private HashMap<String, ItemStack> content = new HashMap<>();

    // Add item to the storage, unless added content exceed capacity.
    public boolean addItem(ItemStack item)
    {
        if(usage + item.getCount() > capacity)
            return false; // exceed capacity
        
        content.compute(item.getName(), (_,v) -> v==null? item : v.updateCount(item.getCount())); 
        usage = usage + item.getCount();
        return true;
    }
    
    // Fetch Item Stack.
    public ImmutableItemStack getItem(String name)
    {
        ItemStack it = content.get(name);
        if(it == null)
            return new ImmutableItemStack();
        return new ImmutableItemStack(it);
    }

    // Remove Items as specified by the stack, unless the requested amount can be met.
    public boolean removeItem(ItemStack item)
    {
        ItemStack it = content.get(item.getName());
        if(it == null)
            return false;
        if(it.getCount() < item.getCount())
            return false;
        
        content.compute(item.getName(), (_,v) -> {
            v= v.updateCount(-item.getCount());
            if(v.getCount() == 0) return null;
            return v;
        });

        usage = usage - item.getCount();

        return true;
    }
}
