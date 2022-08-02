package com.outbox.service.shipment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outbox.service.shipment.model.Order;
import com.outbox.service.shipment.model.Payload;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "data.cdc.pg-outbox-connector.public.outbox")
    public void receive(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        Order order = convertPayloadToOrder(consumerRecord);
        if (order != null) {
            // Shipment operations
            System.out.println(order);
        }
    }

    public Order convertPayloadToOrder(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        var consumeRecordValue = consumerRecord.value();

        if (consumeRecordValue == null) return null;
        var record = objectMapper.readValue(consumeRecordValue, Map.class);
        var payload = (LinkedHashMap) record.get("payload");
        var operation = (String) payload.get("op");
        if (operation.equals("c")) {
            var afterMap = (LinkedHashMap) payload.get("after");
            var after = objectMapper.convertValue(afterMap, Payload.class);
            return objectMapper.readValue(after.getPayload(), Order.class);
        }
        return null;
    }
}
