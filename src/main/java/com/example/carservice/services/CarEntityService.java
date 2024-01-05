package com.example.carservice.services;

import com.example.carservice.dao.CarRepository;
import com.example.carservice.entity.Car;
import org.springframework.stereotype.Service;

@Service
public class CarEntityService extends EntityService<Car>{
    protected CarEntityService(CarRepository repository) {
        super(repository);
    }
}
