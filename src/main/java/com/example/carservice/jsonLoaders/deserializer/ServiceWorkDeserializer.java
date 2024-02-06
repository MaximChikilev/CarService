package com.example.carservice.jsonLoaders.deserializer;

import com.example.carservice.entity.ServiceWork;
import com.example.carservice.entity.SparePart;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import java.lang.reflect.Type;

public class ServiceWorkDeserializer implements JsonDeserializer<ServiceWork> {
  @Override
  public ServiceWork deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    ServiceWork serviceWork = new ServiceWork();
    serviceWork.setName(jsonObject.getAsJsonPrimitive("name").getAsString());
    serviceWork.setDurationInMinutes(jsonObject.getAsJsonPrimitive("durationInMinutes").getAsInt());
    if (jsonObject.has("spareParts")) {
      JsonArray sparePartsArray = jsonObject.getAsJsonArray("spareParts");
      for (JsonElement sparePartElement : sparePartsArray) {
        JsonObject sparePartObject = sparePartElement.getAsJsonObject();
        SparePart sparePart = new SparePart();
        sparePart.setPartNumber(sparePartObject.getAsJsonPrimitive("partNumber").getAsString());
        serviceWork.addSparePart(sparePart);
      }
    }
    return serviceWork;
  }
}
