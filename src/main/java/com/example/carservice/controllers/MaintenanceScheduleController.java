package com.example.carservice.controllers;

import com.example.carservice.entity.MaintenanceSchedule;
import com.example.carservice.services.CarService;
import com.example.carservice.services.ClientService;
import com.example.carservice.services.EntityService;
import com.example.carservice.services.InspectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/maintenanceSchedule")
public class MaintenanceScheduleController extends MyAbstractController<MaintenanceSchedule> {
  private final InspectionService inspectionService;
  private final CarService carService;
  private final ClientService clientService;

  protected MaintenanceScheduleController(
      EntityService<MaintenanceSchedule> service,
      InspectionService inspectionService,
      CarService carService,
      ClientService clientService,
      @Value("${scheduleSearchFields}") String searchFields) {
    super(service, "maintenanceSchedule", searchFields);
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
}
