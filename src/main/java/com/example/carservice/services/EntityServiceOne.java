package com.example.carservice.services;

import com.example.carservice.dao.*;
import com.example.carservice.entity.Car;
import com.example.carservice.entity.Client;
import com.example.carservice.entity.Manufacturer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class EntityServiceOne {
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final MaintenanceScheduleRepository maintenanceScheduleRepository;
    private final ScheduledTechnicalInspectionsRepository scheduledTechnicalInspectionsRepository;
    private final SemitrailerRepository semitrailerRepository;
    private final ServiceWorkRepository serviceWorkRepository;
    private final SparePartRepository sparePartRepository;
    private final StockRepository stockRepository;
    private final ManufacturerRepository manufacturerRepository;

    public EntityServiceOne(CarRepository carRepository, ClientRepository clientRepository, MaintenanceScheduleRepository maintenanceScheduleRepository,
                            ScheduledTechnicalInspectionsRepository scheduledTechnicalInspectionsRepository, SemitrailerRepository semitrailerRepository,
                            ServiceWorkRepository serviceWorkRepository, SparePartRepository sparePartRepository, StockRepository stockRepository, ManufacturerRepository manufacturerRepository) {
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.maintenanceScheduleRepository = maintenanceScheduleRepository;
        this.scheduledTechnicalInspectionsRepository = scheduledTechnicalInspectionsRepository;
        this.semitrailerRepository = semitrailerRepository;
        this.serviceWorkRepository = serviceWorkRepository;
        this.sparePartRepository = sparePartRepository;
        this.stockRepository = stockRepository;
        this.manufacturerRepository = manufacturerRepository;
    }
    @Transactional(readOnly = true)
    public long carCount() {
        return carRepository.count();
    }
    @Transactional(readOnly = true)
    public List<Car> getCars(){
        return carRepository.findAll();
    }
    @Transactional
    public void saveCars(List<Car> carList){
        carRepository.saveAll(carList);
    }
    @Transactional
    public void saveCar(Car car){
        carRepository.save(car);
    }
    @Transactional
    public Car getCarById(Long carId){
        return carRepository.findById(carId).orElse(null);
    }
    @Transactional
    public void deleteCar(Long carId){
        carRepository.deleteById(carId);
    }
    @Transactional(readOnly = true)
    public long manufacturerCount() {
        return carRepository.count();
    }
    @Transactional(readOnly = true)
    public List<Manufacturer> getManufacturers(){
        return manufacturerRepository.findAll();
    }
    @Transactional
    public void saveManufacturer(Manufacturer manufacturer){
        manufacturerRepository.save(manufacturer);
    }
    @Transactional
    public void saveManufacturers(List<Manufacturer> manufacturerList){
        manufacturerRepository.saveAll(manufacturerList);
    }
    @Transactional
    public Manufacturer getManufacturerById(Long id){
       return manufacturerRepository.findById(id).orElse(null);
    }
    @Transactional
    public void deleteManufacturer(Long manufacturerId){
        manufacturerRepository.deleteById(manufacturerId);
    }
}
