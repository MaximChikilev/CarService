package com.example.carservice.jsonLoaders.deserializer;

import com.example.carservice.entity.GpsTrackerData;
import com.example.carservice.services.Utils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class GpsTrackerDataDeserializer implements JsonDeserializer<GpsTrackerData> {

  @Override
  public GpsTrackerData deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    var gpsTrackerData = new GpsTrackerData();
    if (jsonObject.has("car")) {
      String licencePlateNumber = jsonObject.getAsJsonPrimitive("car").getAsString();
      gpsTrackerData.setCar(Utils.getCarWithLicencePlateNumber(licencePlateNumber));
    }
    gpsTrackerData.setDate(Utils.parseDate(jsonObject.getAsJsonPrimitive("date").getAsString()));
    int mileage = jsonObject.getAsJsonPrimitive("mileage").getAsInt();
    gpsTrackerData.setMileage(mileage);
    return gpsTrackerData;
  }
}
