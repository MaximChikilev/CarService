package com.example.carservice.jsonLoaders;

import com.example.carservice.entity.Manufacturer;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ManufacturerJsonFileManager extends JasonFilesManager<Manufacturer>{
    public ManufacturerJsonFileManager(String path) {
        super(path);
        listType = new TypeToken<List<Manufacturer>>() { }.getType();
    }
}
