package org.acme;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.AttributeConverter;

public class JsonObjectConverter implements AttributeConverter<JsonObject, String> {

    @Override
    public String convertToDatabaseColumn(JsonObject arg0) {
        return arg0.toString();
    }

    @Override
    public JsonObject convertToEntityAttribute(String arg0) {
        JsonReader reader = Json.createReader(new StringReader(arg0));
        return reader.readObject();
    }

}