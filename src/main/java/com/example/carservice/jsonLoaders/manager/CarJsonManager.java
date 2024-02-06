package com.example.carservice.jsonLoaders.manager;

import com.example.carservice.entity.Car;
import com.example.carservice.jsonLoaders.deserializer.CarDeserializer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.List;

@Component
public class CarJsonManager extends JsonManager<Car> {
  public CarJsonManager(@Value("${car.path}") String path) {
    super(new File(path));
    listType = new TypeToken<List<Car>>() {}.getType();

    gsonForLoad = new GsonBuilder().registerTypeAdapter(Car.class, new CarDeserializer()).create();
  }
}
