package com.example.carservice.controllers;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.MaintenanceSchedule;
import com.example.carservice.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

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
  protected MaintenanceSchedule getInstanceForModel() {
    return new MaintenanceSchedule();
  }

  @Override
  protected void addAdditionalAttributes(Model model, boolean isDataErrorPresent) {
    model.addAttribute("technicalInspections", inspectionService.getAll());
    model.addAttribute("cars", carService.getAll());
    model.addAttribute("clients", clientService.getAll());
    model.addAttribute("exist", false);
    if (isDataErrorPresent) {
      MaintenanceSchedule maintenanceSchedule =
          ((MaintenanceSchedule) Objects.requireNonNull(model.getAttribute("newEntity")));
      MaintenanceSchedule maintenanceScheduleFromDB =
          ((MaintenanceScheduleService) service)
              .getByMaintenanceDateAndAndMaintenanceTime(
                  maintenanceSchedule.getMaintenanceDate(),
                  maintenanceSchedule.getMaintenanceTime());
      if (maintenanceScheduleFromDB != null) {
        model.addAttribute("exist", true);
      }
    }
  }
}
