package com.example.city_engine.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
 
/**
 *
 * @author root
 */
public class StorageTest {

    public StorageTest() {
    }

    @Test
    void testAddItem()
    {
        var st = new Storage();
        var item = new ItemStack("TestItem",5);
        assertEquals(true, st.addItem(item));
        assertEquals(5, st.getUsage());
    }
    @Test
    void testGetItem()
    {
        var st = new Storage();
        var item = new ItemStack("TestItem",5);
        st.addItem(item);
        var res = st.getItem("TestItem");
        assertEquals(false, res.isNull());
        assertEquals(5, res.getCount());
    }
    @Test
    void testAddItemExceedCapacity()
    {
        var st = new Storage();
        st.setCapacity(1);
        var item = new ItemStack("TestItem",5);
        assertEquals(false, st.addItem(item));
        assertEquals(0, st.getUsage());
    }
    @Test
    void testAddItemIncreaseStack()
    {
        var st = new Storage();
        var item = new ItemStack("TestItem",5);
        var item2 = new ItemStack("TestItem",5);
        st.addItem(item);
        st.addItem(item2);
        var res = st.getItem("TestItem");
        assertEquals(false, res.isNull());
        assertEquals(10, res.getCount());
    }
    @Test
    void testAddItems()
    {
        var st = new Storage();
        var item = new ItemStack("TestItem",5);
        var item2 = new ItemStack("TestItem2",5);
        st.addItem(item);
        st.addItem(item2);
        var res = st.getItem("TestItem");
        assertEquals(false, res.isNull());
        assertEquals(5, res.getCount());
        res = st.getItem("TestItem2");
        assertEquals(false, res.isNull());
        assertEquals(5, res.getCount());
    }




    @Test
    void testRemoveItem()
    {
        var st = new Storage();
        var item = new ItemStack("TestItem",10);
        st.addItem(item);
        assertEquals(10, st.getUsage());
        item = new ItemStack("TestItem",5);
        assertEquals(true,st.removeItem(item));
        assertEquals(5, st.getUsage());
        var res = st.getItem("TestItem");
        assertEquals(false, res.isNull());
        assertEquals(5, res.getCount());
    }
    @Test
    void testRemoveItemExceedCount()
    {
        var st = new Storage();
        var item = new ItemStack("TestItem",10);
        st.addItem(item);
        assertEquals(10, st.getUsage());
        item = new ItemStack("TestItem",15);
        assertEquals(false,st.removeItem(item));
        assertEquals(10, st.getUsage());
        var res = st.getItem("TestItem");
        assertEquals(false, res.isNull());
        assertEquals(10, res.getCount());
    }
    @Test
    void testRemoveItemDeleteItemWhenNecessary()
    {
        var st = new Storage();
        var item = new ItemStack("TestItem",10);
        st.addItem(item);
        assertEquals(10, st.getUsage());
        item = new ItemStack("TestItem",10);
        assertEquals(true,st.removeItem(item));
        assertEquals(0, st.getUsage());
        var res = st.getItem("TestItem");
        assertEquals(true, res.isNull());
    }
}