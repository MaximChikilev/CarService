package com.example.carservice.jsonLoaders.deserializer;

import com.example.carservice.entity.Manufacturer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class ManufacturerDeserializer implements JsonDeserializer<Manufacturer> {
  @Override
  public Manufacturer deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    String name = jsonObject.getAsJsonPrimitive("name").getAsString();
    Manufacturer manufacturer = new Manufacturer();
    manufacturer.setName(name);
    return manufacturer;
  }
}
