package com.Nhom19.shopQuanAo.service;

import com.Nhom19.shopQuanAo.DTO.AddressSnapshot;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AddressJsonConverter
        implements AttributeConverter<AddressSnapshot, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(AddressSnapshot address) {
        try {
            return address == null ? null : mapper.writeValueAsString(address);
        } catch (Exception e) {
            throw new RuntimeException("Convert address to JSON failed", e);
        }
    }

    @Override
    public AddressSnapshot convertToEntityAttribute(String dbData) {
        try {
            return dbData == null ? null :
                    mapper.readValue(dbData, AddressSnapshot.class);
        } catch (Exception e) {
            throw new RuntimeException("Convert JSON to address failed", e);
        }
    }
}
