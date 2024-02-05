package com.example.carservice.controllers;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.GpsTrackerData;
import com.example.carservice.entity.Manufacturer;
import com.example.carservice.jsonLoaders.deserializer.GpsTrackerDataDeserializer;
import com.example.carservice.services.CarService;
import com.example.carservice.services.EntityService;
import com.example.carservice.services.ManufacturerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController extends MyAbstractController<Car> {

    private final EntityService<Manufacturer> manufacturerEntityService;

    protected CarController(CarService service, ManufacturerService manufacturerEntityService, @Value("${carSearchFields}") String searchFields) {
        super(service, "car", searchFields);
        this.manufacturerEntityService = manufacturerEntityService;
    }

    @Override
    protected Car getNewInstance() {
        return new Car();
    }

    @Override
    protected void addAdditionalAttributes(Model model) {
        model.addAttribute("manufacturers", manufacturerEntityService.getAll());
    }

    @PostMapping("/upload/gpsTrackerData")
    public ResponseEntity<String> uploadGpsTrackerData(@RequestBody String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GpsTrackerData.class, new GpsTrackerDataDeserializer())
                .create();
        Type listType = new TypeToken<List<GpsTrackerData>>() {
        }.getType();

        try {
            List<GpsTrackerData> gpsTrackerDataList = gson.fromJson(json, listType);
            ((CarService) service).saveGpsTrackerData(gpsTrackerDataList);
            return new ResponseEntity<>("GpsTrackerData uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing GpsTrackerData: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
