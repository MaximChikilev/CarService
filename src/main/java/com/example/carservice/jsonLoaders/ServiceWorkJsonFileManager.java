package com.example.carservice.jsonLoaders;

import com.example.carservice.entity.ServiceWork;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ServiceWorkJsonFileManager extends JasonFilesManager<ServiceWork>{
    public ServiceWorkJsonFileManager(String path) {
        super(path);
        listType = new TypeToken<List<ServiceWork>>() { }.getType();
    }
}
