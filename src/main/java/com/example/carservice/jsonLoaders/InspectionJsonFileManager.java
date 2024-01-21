package com.example.carservice.jsonLoaders;

import com.example.carservice.entity.TechnicalInspection;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class InspectionJsonFileManager extends JasonFilesManager<TechnicalInspection>{
    public InspectionJsonFileManager(String path) {
        super(path);
        listType = new TypeToken<List<TechnicalInspection>>() { }.getType();
    }
}
