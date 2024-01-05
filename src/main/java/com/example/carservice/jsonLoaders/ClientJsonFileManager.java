package com.example.carservice.jsonLoaders;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Client;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ClientJsonFileManager extends JasonFilesManager<Client>{
    public ClientJsonFileManager(String path) {
        super(path);
        listType = new TypeToken<List<Client>>() { }.getType();
    }
}
