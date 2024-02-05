package com.example.carservice.jsonLoaders.deserializer;

import com.example.carservice.entity.ServiceWork;
import com.example.carservice.entity.SparePart;
import com.example.carservice.entity.TechnicalInspection;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TechnicalInspectionDeserializer implements JsonDeserializer<TechnicalInspection> {
    @Override
    public TechnicalInspection deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        TechnicalInspection technicalInspection = new TechnicalInspection();
        technicalInspection.setName(jsonObject.getAsJsonPrimitive("name").getAsString());
        technicalInspection.setMileageToPass(jsonObject.getAsJsonPrimitive("mileageToPass").getAsInt());
        if (jsonObject.has("serviceWorks")) {
            JsonArray sparePartsArray = jsonObject.getAsJsonArray("serviceWorks");
            for (JsonElement sparePartElement : sparePartsArray) {
                JsonObject sparePartObject = sparePartElement.getAsJsonObject();
                ServiceWork serviceWork = new ServiceWork();
                serviceWork.setName(sparePartObject.getAsJsonPrimitive("name").getAsString());
                technicalInspection.addServiceWork(serviceWork);
            }
        }
        return technicalInspection;
    }
}
