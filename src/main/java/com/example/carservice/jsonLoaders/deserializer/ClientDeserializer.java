package com.example.carservice.jsonLoaders.deserializer;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Client;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;

public class ClientDeserializer implements JsonDeserializer<Client> {
  @Override
  public Client deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    JsonObject jsonObject = jsonElement.getAsJsonObject();

    String firstName = jsonObject.getAsJsonPrimitive("firstName").getAsString();
    String secondName = jsonObject.getAsJsonPrimitive("secondName").getAsString();
    String phoneNumber = jsonObject.getAsJsonPrimitive("phoneNumber").getAsString();
    String email = jsonObject.getAsJsonPrimitive("email").getAsString();
    Car car = null;
    if (jsonObject.has("licensePlateNumber")) {
      String licensePlateNumber = jsonObject.getAsJsonPrimitive("licensePlateNumber").getAsString();
      car = new Car();
      car.setLicensePlateNumber(licensePlateNumber);
    }

    return new Client(firstName, secondName, phoneNumber, email, car);
  }
}
