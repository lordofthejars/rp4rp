package org.acme;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.kafka.common.serialization.Deserializer;

/**
 * JsonDeserializer
 */
public class JsonDeserializer implements Deserializer<JsonObject> {

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
    }

    @Override
    public JsonObject deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try (JsonReader reader = Json.createReader(new ByteArrayInputStream(data))) {
            return reader.readObject();
        }
    }
    
}