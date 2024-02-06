package com.example.carservice.jsonLoaders.manager;

import com.example.carservice.entity.Manufacturer;
import com.example.carservice.jsonLoaders.deserializer.ManufacturerDeserializer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.List;

@Component
public class ManufacturerJsonManager extends JsonManager<Manufacturer> {

  public ManufacturerJsonManager(@Value("${manufacturer.path}") String path) {
    super(new File(path));
    listType = new TypeToken<List<Manufacturer>>() {}.getType();

    gsonForLoad =
        new GsonBuilder()
            .registerTypeAdapter(Manufacturer.class, new ManufacturerDeserializer())
            .create();
  }
}
