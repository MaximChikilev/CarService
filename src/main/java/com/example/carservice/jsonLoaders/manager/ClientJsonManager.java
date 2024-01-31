package com.example.carservice.jsonLoaders.manager;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Client;
import com.example.carservice.jsonLoaders.deserializer.CarDeserializer;
import com.example.carservice.jsonLoaders.deserializer.ClientDeserializer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class ClientJsonManager extends JsonManager<Client> {
    public ClientJsonManager(@Value("${client.path}") String path) {
        super(new File(path));
        listType = new TypeToken<List<Client>>() {
        }.getType();

        gsonForLoad = new GsonBuilder()
                .registerTypeAdapter(Client.class, new ClientDeserializer())
                .create();
    }
}
