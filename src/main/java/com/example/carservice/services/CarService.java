package com.example.carservice.services;

import com.example.carservice.dto.ClientCarRecommendedToServiceDTO;
import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.entity.Client;
import com.example.carservice.entity.GpsTrackerData;
import com.example.carservice.jsonLoaders.manager.CarJsonManager;
import com.example.carservice.repo.CarRepository;
import com.example.carservice.entity.Car;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class CarService extends EntityService<Car> {
  private final ManufacturerService manufacturerService;
  private final CarJsonManager carJsonManager;

  protected CarService(
      CarRepository repository,
      ManufacturerService manufacturerService,
      CarJsonManager carJsonManager) {
    super(repository);
    this.manufacturerService = manufacturerService;
    this.carJsonManager = carJsonManager;
  }

  @Override
  public Long getId(Car entity) {
    return entity.getCarId();
  }

  @Transactional
  @Override
  public boolean isDataErrorPresent(Car entity) {
    boolean result = false;
    String vinCode = entity.getVinCode();
    Car car = getByVinCode(vinCode);
    if ((car != null) && ((getId(entity) == null)||(!entity.getCarId().equals(car.getCarId())))) {
      result = true;
    }
    return result;
  }

  @Override
  protected Map<String, Function<String, List<Car>>> setSearchFieldsAndCorrespondingMethods() {
    methodMap.put(
        "License plate number",
        ((CarRepository) repository)::findAllByLicensePlateNumberContaining);
    methodMap.put("Model", ((CarRepository) repository)::findAllByModelContaining);
    methodMap.put("Vin code", ((CarRepository) repository)::findAllByVinCodeContaining);
    methodMap.put(
        "Manufacturer name", ((CarRepository) repository)::findAllByManufacturerNameContaining);
    return methodMap;
  }

  @Transactional
  @Override
  public List<Car> loadEntityListFromJson() throws IOException {
    var cars = carJsonManager.loadListFromFile();
    for (Car car : cars) {
      if (car.getManufacturer() != null) {
        car.setManufacturer(manufacturerService.getByName(car.getManufacturer().getName()));
      }
    }
    return cars;
  }

  @Transactional
  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    List<ConnectionsWithOtherEntityDTO> list = new ArrayList<>();
    list.addAll(((CarRepository) repository).getConnectionWithClients(id));
    list.addAll(((CarRepository) repository).getConnectionWithSchedule(id));
    list.addAll(((CarRepository) repository).getConnectionWithGpsTrackerData(id));
    return list;
  }

  @Transactional
  public Car getByLicencePlateNumber(String licencePlateNumber) {
    return ((CarRepository) repository).findByLicensePlateNumber(licencePlateNumber);
  }

  @Transactional
  public Car getByVinCode(String vinCode) {
    return ((CarRepository) repository).findByVinCode(vinCode);
  }

  @Transactional
  public Client getClientByCar(Car car) {
    return ((CarRepository) repository).findCarOwner(car);
  }

  @Transactional
  public void saveGpsTrackerData(List<GpsTrackerData> list) {
    List<Car> carList = new ArrayList<>();
    for (GpsTrackerData element : list) {
      var carFromDB = getByLicencePlateNumber(element.getCar().getLicensePlateNumber());
      carFromDB.setMileage(carFromDB.getMileage() + element.getMileage());
      carList.add(carFromDB);
    }
    repository.saveAll(carList);
  }

  @Transactional
  public boolean isCarRegisteredToInspection(
      ClientCarRecommendedToServiceDTO clientCarRecommendedToService) {
    return ((CarRepository) repository)
            .countByDateAndTechnicalInspectionAndCar(
                Utils.getTodayDate(),
                clientCarRecommendedToService.getInspectionName(),
                clientCarRecommendedToService.getClientCarAVGMileageDTO().getLicensePlateNumber())
        != 0;
  }
}
