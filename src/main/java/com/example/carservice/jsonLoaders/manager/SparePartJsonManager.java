package com.example.carservice.jsonLoaders.manager;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.SparePart;
import com.example.carservice.jsonLoaders.deserializer.CarDeserializer;
import com.example.carservice.jsonLoaders.deserializer.SparePartDeserializer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
@Component
public class SparePartJsonManager extends JsonManager<SparePart>{
    public SparePartJsonManager(@Value("${sparePart.path}")String path) {
        super(new File(path));
        listType = new TypeToken<List<SparePart>>() { }.getType();

        gsonForLoad = new GsonBuilder()
                .registerTypeAdapter(SparePart.class, new SparePartDeserializer())
                .create();
    }
}
