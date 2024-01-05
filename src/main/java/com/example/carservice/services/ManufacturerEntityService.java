package com.example.carservice.services;

import com.example.carservice.dao.ManufacturerRepository;
import com.example.carservice.entity.Manufacturer;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerEntityService extends EntityService<Manufacturer>{
    protected ManufacturerEntityService(ManufacturerRepository repository) {
        super(repository);
    }
}
