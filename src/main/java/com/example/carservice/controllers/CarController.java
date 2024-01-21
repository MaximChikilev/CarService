package com.example.carservice.controllers;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Manufacturer;
import com.example.carservice.services.CarService;
import com.example.carservice.services.EntityService;
import com.example.carservice.services.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController extends MyAbstractController<Car> {
    private final EntityService<Manufacturer> manufacturerEntityService;

    protected CarController(CarService service, ManufacturerService manufacturerEntityService) {
        super(service, "car");
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

    @Override
    protected List<String> getListPossibleSearchFields() {
        return new ArrayList<>(Arrays.asList("License plate number","Manufacturer year","Model","Vin code","Manufacturer name"));
    }
}
