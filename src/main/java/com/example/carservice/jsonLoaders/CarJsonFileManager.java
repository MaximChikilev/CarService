package com.example.carservice.jsonLoaders;

import com.example.carservice.entity.Car;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class CarJsonFileManager extends JasonFilesManager<Car> {
    public CarJsonFileManager(String path) {
        super(path);
        listType = new TypeToken<List<Car>>() { }.getType();
    }
}
