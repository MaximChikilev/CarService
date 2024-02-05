package com.example.carservice.jsonLoaders.manager;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.GpsTrackerData;
import com.example.carservice.jsonLoaders.deserializer.CarDeserializer;
import com.example.carservice.jsonLoaders.deserializer.GpsTrackerDataDeserializer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
@Component

public class GpsTrackerDataJsonManager extends JsonManager<GpsTrackerData>{
    public GpsTrackerDataJsonManager(@Value("${gpstracker.path}")String path) {
        super(new File(path));
        listType = new TypeToken<List<GpsTrackerData>>() {
        }.getType();

        gsonForLoad = new GsonBuilder()
                .registerTypeAdapter(GpsTrackerData.class, new GpsTrackerDataDeserializer())
                .create();
    }
}
