package org.acme;

import javax.json.JsonObject;

public class OutboxEvent {

    private Long entityId;
    private String eventType;
    private JsonObject content;

    public OutboxEvent(Long entityId, String eventType, JsonObject content) {
        this.entityId = entityId;
        this.eventType = eventType;
        this.content = content;
    }

    public Long getEntityId() {
        return entityId;
    }

    public String getEventType() {
        return eventType;
    }

    public JsonObject getContent() {
        return content;
    }

}