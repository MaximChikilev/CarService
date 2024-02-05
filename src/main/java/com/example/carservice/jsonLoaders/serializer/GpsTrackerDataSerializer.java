package com.example.carservice.jsonLoaders.serializer;

import com.example.carservice.entity.GpsTrackerData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GpsTrackerDataSerializer implements JsonSerializer<GpsTrackerData> {
    @Override
    public JsonElement serialize(GpsTrackerData gpsTrackerData, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("car", gpsTrackerData.getCar().getLicensePlateNumber());
        jsonObject.addProperty("date", formatDate(gpsTrackerData.getDate()));
        jsonObject.addProperty("mileage", gpsTrackerData.getMileage());
        return jsonObject;
    }
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
