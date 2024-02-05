package com.example.carservice.jsonLoaders;

import com.example.carservice.entity.Stock;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class StockJsonFileManager extends JasonFilesManager<Stock>{

    public StockJsonFileManager(String path) {
        super(path);
        listType = new TypeToken<List<Stock>>() { }.getType();
    }
}
