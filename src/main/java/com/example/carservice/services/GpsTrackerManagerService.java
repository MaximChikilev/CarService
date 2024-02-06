package com.example.carservice.services;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.GpsTrackerData;
import com.example.carservice.jsonLoaders.serializer.GpsTrackerDataSerializer;
import com.example.carservice.repo.GpsTrackerDataRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class GpsTrackerManagerService {
  private static final String UPLOAD_URL = "http://localhost:8888/car/upload/gpsTrackerData";
  private final CarService carService;
  private final GpsTrackerDataRepository gpsTrackerDataRepository;

  public GpsTrackerManagerService(
      CarService carService, GpsTrackerDataRepository gpsTrackerDataRepository) {
    this.carService = carService;
    this.gpsTrackerDataRepository = gpsTrackerDataRepository;
  }

  public void uploadGpsTrackerData() {
    Gson gsonForGpsTrackerUpload =
        new GsonBuilder()
            .registerTypeAdapter(GpsTrackerData.class, new GpsTrackerDataSerializer())
            .create();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    String jsonGpsTrackerData = gsonForGpsTrackerUpload.toJson(generateDailyGpsTrackerData());
    HttpEntity<String> requestEntity = new HttpEntity<>(jsonGpsTrackerData, headers);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(UPLOAD_URL, requestEntity, String.class);
  }

  private List<GpsTrackerData> generateDailyGpsTrackerData() {
    Random random = new Random();
    var list = new ArrayList<GpsTrackerData>();
    var carList = carService.getAll();
    for (Car car : carList) {
      list.add(new GpsTrackerData(car, new Date(), random.nextInt(500)));
    }
    gpsTrackerDataRepository.saveAll(list);
    return list;
  }
}
