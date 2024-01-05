package com.example.carservice.jsonLoaders;

import com.example.carservice.entity.ScheduledTechnicalInspections;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class InspectionJsonFileManager extends JasonFilesManager<ScheduledTechnicalInspections>{
    public InspectionJsonFileManager(String path) {
        super(path);
        listType = new TypeToken<List<ScheduledTechnicalInspections>>() { }.getType();
    }
}
