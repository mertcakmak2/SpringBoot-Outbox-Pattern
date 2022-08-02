package com.outbox.service.order.model;

import com.outbox.service.order.JsonToMapConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "outbox")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String event;
    private int eventId;
    @Convert(converter = JsonToMapConverter.class)
    private Map<String, Object> payload;
    private Date createdAt;

}
