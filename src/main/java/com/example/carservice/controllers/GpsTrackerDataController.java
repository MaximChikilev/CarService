package com.example.carservice.controllers;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.GpsTrackerData;
import com.example.carservice.services.CarService;
import com.example.carservice.services.EntityService;
import com.example.carservice.services.GpsTrackerDataService;
import com.example.carservice.services.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Objects;

@Controller
@RequestMapping("/gpsTracker")
public class GpsTrackerDataController extends MyAbstractController<GpsTrackerData> {
  private final CarService carService;

  protected GpsTrackerDataController(
      EntityService<GpsTrackerData> service,
      CarService carService,
      @Value("${gpsTrackerSearchFields}") String searchFields) {
    super(service, "gpsTracker", searchFields);
    this.carService = carService;
  }

  @Override
  protected GpsTrackerData getInstanceForModel() {
    return new GpsTrackerData();
  }

  @Override
  protected void addAdditionalAttributes(Model model, boolean isDataErrorPresent) {
    model.addAttribute("cars", carService.getAll());
    model.addAttribute("exist", false);
    if (isDataErrorPresent) {
      GpsTrackerData gpsTrackerData =
          ((GpsTrackerData) Objects.requireNonNull(model.getAttribute("newEntity")));
      GpsTrackerData gpsTrackerDataFromDB =
          ((GpsTrackerDataService) service)
              .getByLicencePlateNumberAndDate(
                  gpsTrackerData.getCar(), gpsTrackerData.getDate());
      if (gpsTrackerDataFromDB != null) {
        model.addAttribute("exist", true);
      }
      if(Utils.isDateAfterCurrent(gpsTrackerData.getDate())) model.addAttribute("dateAfterCurrent", true);
    }
  }
}
