package com.example.carservice.services;

import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.entity.Car;
import com.example.carservice.entity.GpsTrackerData;
import com.example.carservice.jsonLoaders.manager.GpsTrackerDataJsonManager;
import com.example.carservice.repo.GpsTrackerDataRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class GpsTrackerDataService extends EntityService<GpsTrackerData> {
  private final GpsTrackerDataJsonManager gpsTrackerDataJsonManager;
  private final CarService carService;

  protected GpsTrackerDataService(
      JpaRepository<GpsTrackerData, Long> repository,
      GpsTrackerDataJsonManager gpsTrackerDataJsonManager,
      CarService carService) {
    super(repository);
    this.gpsTrackerDataJsonManager = gpsTrackerDataJsonManager;
    this.carService = carService;
  }

  @Override
  public Long getId(GpsTrackerData entity) {
    return entity.getId();
  }

  @Transactional
  @Override
  public boolean isDataErrorPresent(GpsTrackerData entity) {
    boolean result = false;
    GpsTrackerData gpsTrackerData =
        getByLicencePlateNumberAndDate(entity.getCar(), entity.getDate());
    if ((gpsTrackerData != null) && ((getId(entity) == null)||(!entity.getId().equals(gpsTrackerData.getId())))) result = true;
    if (Utils.isDateAfterCurrent(entity.getDate())) result = true;
    return result;
  }

  @Override
  protected Map<String, Function<String, List<GpsTrackerData>>>
      setSearchFieldsAndCorrespondingMethods() {
    methodMap.put(
        "License plate number",
        ((GpsTrackerDataRepository) repository)::findAllByCarLicensePlateNumberContaining);
    return methodMap;
  }

  @Transactional
  @Override
  public List<GpsTrackerData> loadEntityListFromJson() throws IOException {
    var gpsTrackerDataList = gpsTrackerDataJsonManager.loadListFromFile();
    for (GpsTrackerData element : gpsTrackerDataList) {
      if (element.getCar() != null) {
        element.setCar(
            carService.getByLicencePlateNumber(element.getCar().getLicensePlateNumber()));
      }
    }
    return gpsTrackerDataList;
  }

  @Transactional
  @Override
  public List<ConnectionsWithOtherEntityDTO> getConnectionsWithOtherTables(Long id) {
    return null;
  }

  public GpsTrackerData getByLicencePlateNumberAndDate(Car car, Date date) {
    return ((GpsTrackerDataRepository) repository).findByCarLicensePlateNumberAndAndDate(car, date);
  }
}
