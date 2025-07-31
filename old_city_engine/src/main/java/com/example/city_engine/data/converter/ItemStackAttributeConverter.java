package com.example.city_engine.data.converter;

import com.example.city_engine.data.ItemStack;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ItemStackAttributeConverter implements AttributeConverter<ItemStack, String>{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ItemStack address) {
        try {
            return objectMapper.writeValueAsString(address);
        } catch (JsonProcessingException jpe) {
            return null;
        }
    }

    @Override
    public ItemStack convertToEntityAttribute(String value) {
        try {
            return objectMapper.readValue(value, ItemStack.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
