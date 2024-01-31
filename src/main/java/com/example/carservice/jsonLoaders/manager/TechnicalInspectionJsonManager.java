package com.example.carservice.jsonLoaders.manager;

import com.example.carservice.entity.TechnicalInspection;
import com.example.carservice.jsonLoaders.deserializer.TechnicalInspectionDeserializer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
@Component
public class TechnicalInspectionJsonManager extends JsonManager<TechnicalInspection>{
    public TechnicalInspectionJsonManager(@Value("${inspection.path}")String path) {
        super(new File(path));
        listType = new TypeToken<List<TechnicalInspection>>() { }.getType();

        gsonForLoad = new GsonBuilder()
                .registerTypeAdapter(TechnicalInspection.class, new TechnicalInspectionDeserializer())
                .create();
    }
}
