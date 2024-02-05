package com.example.carservice.services;

import com.example.carservice.entity.Car;
import com.example.carservice.jsonLoaders.manager.ManufacturerJsonManager;
import com.example.carservice.repo.ManufacturerRepository;
import com.example.carservice.entity.Manufacturer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class ManufacturerService extends EntityService<Manufacturer>{
    private final ManufacturerJsonManager manufacturerJsonManager;
    protected ManufacturerService(ManufacturerRepository repository, ManufacturerJsonManager manufacturerJsonManager) {
        super(repository);
        this.manufacturerJsonManager = manufacturerJsonManager;
    }

    @Override
    protected Map<String, Function<String, List<Manufacturer>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("Name",((ManufacturerRepository) repository)::findAllByNameContaining);
        return methodMap;
    }

    @Override
    protected List<Manufacturer> loadEntityListFromJson() throws IOException {
        return manufacturerJsonManager.loadListFromFile();
    }
    public Manufacturer getByName(String name){
        return ((ManufacturerRepository) repository).findByName(name);
    }


}
