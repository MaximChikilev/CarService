package com.example.carservice.controllers;

import com.example.carservice.dto.CarDateTimeChoiceDTO;
import com.example.carservice.dto.FreeDataTimeForInspectionDTO;
import com.example.carservice.entity.CustomUser;
import com.example.carservice.entity.MaintenanceSchedule;
import com.example.carservice.entity.UserRole;
import com.example.carservice.services.ClientService;
import com.example.carservice.services.MaintenanceScheduleService;
import com.example.carservice.services.UserService;
import com.example.carservice.services.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.text.ParseException;
import java.util.List;

@Controller
public class ClientPageController {
  private final UserService userService;
  private final Utils utils;
  private final ClientService clientService;
  private final MaintenanceScheduleService maintenanceScheduleService;

  @Value("${daysForScheduleGenerator}")
  private int daysForScheduleGenerator;

  public ClientPageController(
      UserService userService,
      Utils utils,
      ClientService clientService,
      MaintenanceScheduleService maintenanceScheduleService) {
    this.userService = userService;
    this.utils = utils;
    this.clientService = clientService;
    this.maintenanceScheduleService = maintenanceScheduleService;
  }

  @GetMapping("/clientPage")
  public String getClientPage(Model model) {
    initializeModelAttributes(model);
    return "clientPage";
  }

  @PostMapping("/clientPage/saveUserChanges")
  public String saveUserChanges(Model model, @ModelAttribute CustomUser user) {
    user.setRole(UserRole.ROLE_CLIENT);
    userService.save(user);
    initializeModelAttributes(model);
    return "clientPage";
  }

  @PostMapping("/clientPage/singUpForAppointment")
  public String signUp(Model model, @ModelAttribute CarDateTimeChoiceDTO entity)
      throws ParseException {
    maintenanceScheduleService.signUpNewInspection(entity);
    initializeModelAttributes(model);
    model.addAttribute(
        "inspectionsSchedule",
        maintenanceScheduleService.findByLicensePlateNumberAndDate(
            entity.getSelectedLicensePlateNumber()));
    return "clientPage";
  }

  private Model initializeModelAttributes(Model model) {
    CustomUser user = userService.getByLogin(utils.getCurrentUser().getUsername());
    List<String> licensePlateNumbers = clientService.getLicensePlateNumbersByEmail(user.getEmail());
    var untilScheduleDate = utils.getDateNowPlusSomeDays(daysForScheduleGenerator);
    FreeDataTimeForInspectionDTO freeDataTimes =
        maintenanceScheduleService.getFreeMaintenanceSchedulesUntilDate(untilScheduleDate);
    model.addAttribute("User", user);
    model.addAttribute("licensePlateNumbers", licensePlateNumbers);
    if (licensePlateNumbers.isEmpty()) {
      model.addAttribute("hasCars", false);
    } else {
      model.addAttribute("hasCars", true);
    }
    model.addAttribute("freeDataTimes", freeDataTimes.getFreeDateTime());
    model.addAttribute("clientScheduleData", new CarDateTimeChoiceDTO());

    return model;
  }
}
