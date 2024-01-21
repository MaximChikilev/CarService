package com.example.carservice.services;

import com.example.carservice.repo.CarRepository;
import com.example.carservice.entity.Car;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class CarService extends EntityService<Car>{
    protected CarService(CarRepository repository) {
        super(repository);
    }

    @Override
    protected Map<String, Function<String, List<Car>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("License plate number",((CarRepository) repository)::findAllByLicensePlateNumberContaining);
        methodMap.put("Manufacturer year",((CarRepository) repository)::findAllByManufacturerYear);
        methodMap.put("Model",((CarRepository) repository)::findAllByModelContaining);
        methodMap.put("Vin code",((CarRepository) repository)::findAllByVinCodeContaining);
        methodMap.put("Manufacturer name",((CarRepository) repository)::findAllByManufacturerNameContaining);
        return methodMap;
    }
}
