package com.example.carservice.jsonLoaders.manager;

import com.example.carservice.entity.MaintenanceSchedule;
import com.example.carservice.jsonLoaders.deserializer.ScheduleDeserializer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
@Component
public class ScheduleJsonManager extends JsonManager<MaintenanceSchedule> {
    public ScheduleJsonManager(@Value("${schedule.path}") String path) {
        super(new File(path));
        listType = new TypeToken<List<MaintenanceSchedule>>() {
        }.getType();

        gsonForLoad = new GsonBuilder()
                .registerTypeAdapter(MaintenanceSchedule.class, new ScheduleDeserializer())
                .create();
    }
}
