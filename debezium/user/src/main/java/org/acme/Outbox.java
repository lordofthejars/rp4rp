package org.acme;

import java.util.Date;

import javax.json.JsonObject;
import javax.persistence.Convert;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name = "outbox")
public class Outbox extends PanacheEntity {

    public Long aggregateId;
    public String eventType;
    @Convert(converter = JsonObjectConverter.class)
    public JsonObject content;
    public Date eventDate;
    
}