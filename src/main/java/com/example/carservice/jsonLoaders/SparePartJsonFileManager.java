package com.example.carservice.jsonLoaders;

import com.example.carservice.entity.SparePart;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SparePartJsonFileManager extends JasonFilesManager<SparePart>{
    public SparePartJsonFileManager(String path) {
        super(path);
        listType = new TypeToken<List<SparePart>>() { }.getType();
    }
}
