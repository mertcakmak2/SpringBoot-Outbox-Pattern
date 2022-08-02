package com.outbox.service.order;

import java.io.IOException;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class JsonToMapConverter implements AttributeConverter<Map<String, Object>, String> {
    private static final Logger logger = LoggerFactory.getLogger(JsonToMapConverter.class);

    @Override
    public String convertToDatabaseColumn(Map<String, Object> customerInfo) {

        String customerInfoJson = null;
        try {
            customerInfoJson = new ObjectMapper().writeValueAsString(customerInfo);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String customerInfoJSON) {

        Map<String, Object> customerInfo = null;
        try {
            customerInfo = new ObjectMapper().readValue(customerInfoJSON, Map.class);
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return customerInfo;
    }
}

