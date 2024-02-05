package com.example.carservice.jsonLoaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;


public abstract class JasonFilesManager<T> {
    protected String path;
    protected Type listType;
    private Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();

    public JasonFilesManager(String path) {
        this.path = path;
    }

    public List<T> loadJsonListFromFile() throws IOException {
        try (Reader reader = new FileReader(path)) {
            List<T> newList = gson.fromJson(reader, listType);
            return newList;
        }
    }
    public void saveJsonListToFile(List<T> list) throws IOException {
        String jsonString = new Gson().toJson(list);
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(jsonString);
        }
    }
}
