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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
@RequestMapping("/car")
public class CarController extends MyAbstractController<Car> {

  private final EntityService<Manufacturer> manufacturerEntityService;

  protected CarController(
      CarService service,
      ManufacturerService manufacturerEntityService,
      @Value("${carSearchFields}") String searchFields) {
    super(service, "car", searchFields);
    this.manufacturerEntityService = manufacturerEntityService;
  }

  @Override
  protected Car getInstanceForModel() {
    return new Car();
  }

  @Override
  protected void addAdditionalAttributes(Model model, boolean isDataErrorPresent) {
    model.addAttribute("manufacturers", manufacturerEntityService.getAll());
    model.addAttribute("exist", false);
    if (isDataErrorPresent) {
      Car car = ((Car) Objects.requireNonNull(model.getAttribute("newEntity")));
      Car carFromDB = ((CarService) service).getByVinCode(car.getVinCode());
      if (carFromDB != null) {
        model.addAttribute("exist", true);
      }
    }
  }
}
