package com.example.carservice.jsonLoaders.deserializer;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Manufacturer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;

public class CarDeserializer implements JsonDeserializer<Car> {

  public CarDeserializer() {}

  @Override
  public Car deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {

    JsonObject jsonObject = jsonElement.getAsJsonObject();
    String licensePlateNumber = jsonObject.getAsJsonPrimitive("licensePlateNumber").getAsString();
    String model = jsonObject.getAsJsonPrimitive("model").getAsString();
    String vinCode = jsonObject.getAsJsonPrimitive("vinCode").getAsString();
    int manufactureYear = jsonObject.getAsJsonPrimitive("manufactureYear").getAsInt();
    int millage = jsonObject.getAsJsonPrimitive("mileage").getAsInt();

    Manufacturer manufacturer = null;
    if (jsonObject.has("manufacturerName")) {
      String manufacturerName = jsonObject.getAsJsonPrimitive("manufacturerName").getAsString();
      manufacturer = new Manufacturer();
      manufacturer.setName(manufacturerName);
    }

    return new Car(licensePlateNumber, model, vinCode, manufactureYear, manufacturer, millage);
  }
}
