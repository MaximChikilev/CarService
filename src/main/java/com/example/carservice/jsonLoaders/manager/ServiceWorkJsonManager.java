package com.example.carservice.jsonLoaders.manager;

import com.example.carservice.entity.ServiceWork;
import com.example.carservice.jsonLoaders.deserializer.ServiceWorkDeserializer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.List;

@Component
public class ServiceWorkJsonManager extends JsonManager<ServiceWork> {
  public ServiceWorkJsonManager(@Value("${serviceWork.path}") String path) {
    super(new File(path));
    listType = new TypeToken<List<ServiceWork>>() {}.getType();

    gsonForLoad =
        new GsonBuilder()
            .registerTypeAdapter(ServiceWork.class, new ServiceWorkDeserializer())
            .create();
  }
}
