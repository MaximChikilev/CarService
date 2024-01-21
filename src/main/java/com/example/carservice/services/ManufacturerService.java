package com.example.carservice.services;

import com.example.carservice.repo.ManufacturerRepository;
import com.example.carservice.entity.Manufacturer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class ManufacturerService extends EntityService<Manufacturer>{
    protected ManufacturerService(ManufacturerRepository repository) {
        super(repository);
    }

    @Override
    protected Map<String, Function<String, List<Manufacturer>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("Name",((ManufacturerRepository) repository)::findAllByNameContaining);
        return methodMap;
    }
}
