package com.example.carservice.controllers;

import com.example.carservice.entity.MaintenanceSchedule;
import com.example.carservice.services.CarService;
import com.example.carservice.services.ClientService;
import com.example.carservice.services.EntityService;
import com.example.carservice.services.InspectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Controller
@RequestMapping("/maintenanceSchedule")
public class MaintenanceScheduleController extends MyAbstractController<MaintenanceSchedule>{
    private final InspectionService inspectionService;
    private final CarService carService;
    private final ClientService clientService;
    protected MaintenanceScheduleController(EntityService<MaintenanceSchedule> service, InspectionService inspectionService, CarService carService, ClientService clientService) {
        super(service, "maintenanceSchedule");
        this.inspectionService = inspectionService;
        this.carService = carService;
        this.clientService = clientService;
    }

    @Override
    protected MaintenanceSchedule getNewInstance() {
        return new MaintenanceSchedule();
    }

    @Override
    protected void addAdditionalAttributes(Model model) {
        model.addAttribute("technicalInspections", inspectionService.getAll());
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("clients", clientService.getAll());
    }

    @Override
    protected List<String> getListPossibleSearchFields() {
        return new ArrayList<>(Arrays.asList("Technical inspection name","Car license plate number","Client phone number"));
    }
}
