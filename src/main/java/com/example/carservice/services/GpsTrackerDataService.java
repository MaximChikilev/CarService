package com.example.carservice.services;

import com.example.carservice.entity.GpsTrackerData;
import com.example.carservice.jsonLoaders.manager.GpsTrackerDataJsonManager;
import com.example.carservice.repo.GpsTrackerDataRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
@Service

public class GpsTrackerDataService extends EntityService<GpsTrackerData> {
    private final GpsTrackerDataJsonManager gpsTrackerDataJsonManager;
    private final CarService carService;

    protected GpsTrackerDataService(JpaRepository<GpsTrackerData, Long> repository, GpsTrackerDataJsonManager gpsTrackerDataJsonManager, CarService carService) {
        super(repository);
        this.gpsTrackerDataJsonManager = gpsTrackerDataJsonManager;
        this.carService = carService;
    }

    @Override
    protected Map<String, Function<String, List<GpsTrackerData>>> setSearchFieldsAndCorrespondingMethods() {
        methodMap.put("License plate number", ((GpsTrackerDataRepository) repository)::findAllByCarLicensePlateNumberContaining);
        return methodMap;
    }

    @Override
    protected List<GpsTrackerData> loadEntityListFromJson() throws IOException {
        var gpsTrackerDataList = gpsTrackerDataJsonManager.loadListFromFile();
        for (GpsTrackerData element : gpsTrackerDataList) {
            if (element.getCar() != null) {
                element.setCar(carService.getByLicencePlateNumber(element.getCar().getLicensePlateNumber()));
            }
        }
        return gpsTrackerDataList;
    }
}
