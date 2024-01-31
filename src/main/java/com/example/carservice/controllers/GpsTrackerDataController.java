package com.example.carservice.controllers;

import com.example.carservice.entity.GpsTrackerData;
import com.example.carservice.services.CarService;
import com.example.carservice.services.EntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Controller
@RequestMapping("/gpsTracker")

public class GpsTrackerDataController extends MyAbstractController<GpsTrackerData>{
    private final CarService carService;
    protected GpsTrackerDataController(EntityService<GpsTrackerData> service, CarService carService) {
        super(service, "gpsTracker");
        this.carService = carService;
    }

    @Override
    protected GpsTrackerData getNewInstance() {
        return new GpsTrackerData();
    }

    @Override
    protected void addAdditionalAttributes(Model model) {
        model.addAttribute("cars", carService.getAll());
    }

    @Override
    protected List<String> getListPossibleSearchFields() {
        return new ArrayList<>(Arrays.asList("License plate number"));
    }
}
