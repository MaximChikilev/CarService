package com.example.carservice.jsonLoaders.deserializer;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Manufacturer;
import com.example.carservice.services.CarService;
import com.example.carservice.services.ClientService;
import com.example.carservice.services.InspectionService;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CarDeserializer implements JsonDeserializer<Car> {

    public CarDeserializer() {

    }

    @Override
    public Car deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
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

        Car car = new Car(licensePlateNumber, model, vinCode, manufactureYear, manufacturer, millage);
        return car;
    }
}
