package com.example.carservice.controllers;

import com.example.carservice.entity.Manufacturer;
import com.example.carservice.services.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController extends MyAbstractController<Manufacturer>{
    protected ManufacturerController(ManufacturerService service) {
        super(service, "manufacturer");
    }

    @Override
    protected Manufacturer getNewInstance() {
        return new Manufacturer();
    }

    @Override
    protected void addAdditionalAttributes(Model model) {

    }

    @Override
    protected List<String> getListPossibleSearchFields() {
        return new ArrayList<>(Arrays.asList("Name"));
    }
}
