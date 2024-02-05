package com.example.carservice.jsonLoaders.deserializer;

import com.example.carservice.entity.Manufacturer;
import com.example.carservice.entity.SparePart;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SparePartDeserializer implements JsonDeserializer<SparePart> {
    @Override
    public SparePart deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String partNumber = jsonObject.getAsJsonPrimitive("partNumber").getAsString();
        String name = jsonObject.getAsJsonPrimitive("name").getAsString();

        Manufacturer manufacturer = null;
        if (jsonObject.has("manufacturerName")) {
            String manufacturerName = jsonObject.getAsJsonPrimitive("manufacturerName").getAsString();
            manufacturer = new Manufacturer();
            manufacturer.setName(manufacturerName);
        }

        SparePart sparePart = new SparePart(partNumber,name,manufacturer);
        return sparePart;

    }
}
