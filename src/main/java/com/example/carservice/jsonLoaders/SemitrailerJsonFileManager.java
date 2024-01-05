package com.example.carservice.jsonLoaders;

import com.example.carservice.entity.Semitrailer;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SemitrailerJsonFileManager extends JasonFilesManager<Semitrailer>{
    public SemitrailerJsonFileManager(String path) {
        super(path);
        listType = new TypeToken<List<Semitrailer>>() { }.getType();
    }
}
