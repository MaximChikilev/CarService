package com.example.carservice.services;

import com.example.carservice.dto.ClientCarRecommendedToServiceDTO;
import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.entity.GpsTrackerData;
import com.example.carservice.jsonLoaders.manager.CarJsonManager;
import com.example.carservice.repo.CarRepository;
import com.example.carservice.entity.Car;
import org.springframework.stereotype.Service;
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
  protected Map<String, Function<String, List<Car>>> setSearchFieldsAndCorrespondingMethods() {
    methodMap.put(
        "License plate number",
        ((CarRepository) repository)::findAllByLicensePlateNumberContaining);
    methodMap.put("Manufacturer year", ((CarRepository) repository)::findAllByManufacturerYear);
    methodMap.put("Model", ((CarRepository) repository)::findAllByModelContaining);
    methodMap.put("Vin code", ((CarRepository) repository)::findAllByVinCodeContaining);
    methodMap.put(
        "Manufacturer name", ((CarRepository) repository)::findAllByManufacturerNameContaining);
    return methodMap;
  }

  @Override
  protected List<Car> loadEntityListFromJson() throws IOException {
    var cars = carJsonManager.loadListFromFile();
    for (Car car : cars) {
      if (car.getManufacturer() != null) {
        car.setManufacturer(manufacturerService.getByName(car.getManufacturer().getName()));
      }
    }
    return cars;
  }

  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    List<ConnectionsWithOtherEntityDTO> list = new ArrayList<>();
    list.addAll(((CarRepository)repository).getConnectionWithClients(id));
    list.addAll(((CarRepository)repository).getConnectionWithSchedule(id));
    return list;
  }

  public Car getByLicencePlateNumber(String licencePlateNumber) {
    return ((CarRepository) repository).findByLicensePlateNumber(licencePlateNumber);
  }
  public List<Car> getCarWithoutOwners(){
    return ((CarRepository) repository).findCarsWithoutOwners();
  }

  public void saveGpsTrackerData(List<GpsTrackerData> list) {
    List<Car> carList = new ArrayList<>();
    for (GpsTrackerData element : list) {
      var carFromDB = getByLicencePlateNumber(element.getCar().getLicensePlateNumber());
      carFromDB.setMileage(carFromDB.getMileage() + element.getMileage());
      carList.add(carFromDB);
    }
    repository.saveAll(carList);
  }

  public boolean isCarRegisteredToInspection(
      ClientCarRecommendedToServiceDTO clientCarRecommendedToService) {
    return ((CarRepository) repository)
            .countByDateAndTechnicalInspectionAndCar(
                Utils.getTodayDate(),
                clientCarRecommendedToService.getInspectionName(),
                clientCarRecommendedToService.getClientCarAVGMileageDTO().getLicensePlateNumber()) != 0;
  }
}
