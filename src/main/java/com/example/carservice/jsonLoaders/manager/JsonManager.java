package com.example.carservice.jsonLoaders.manager;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public abstract class JsonManager<T> {
    //protected String path;
    protected File file;
    protected Type listType;
    protected Gson gsonForLoad;

    public JsonManager(File file) {
        this.file = file;
    }

    public List<T> loadListFromFile() throws IOException {
        try (Reader reader = new FileReader(file)) {
            List<T> newList = gsonForLoad.fromJson(reader, listType);
            return newList;
        }
    }
}
