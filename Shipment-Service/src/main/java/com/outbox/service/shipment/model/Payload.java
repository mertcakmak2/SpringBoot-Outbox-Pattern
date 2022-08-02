package com.outbox.service.shipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payload{

    int id;
    String event;
    @JsonProperty("event_id")
    int eventId;
    String payload;
    @JsonProperty("created_at")
    String createdAt;

    @Override
    public String toString() {
        return "PayLoad [id=" + id + ", event=" + event + ", eventId=" + eventId + ", payload=" + payload
                + ", createdAt=" + createdAt + "]";
    }



}
