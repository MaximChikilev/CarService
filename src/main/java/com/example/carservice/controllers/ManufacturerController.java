package com.example.carservice.controllers;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Manufacturer;
import com.example.carservice.services.CarService;
import com.example.carservice.services.ManufacturerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController extends MyAbstractController<Manufacturer> {
  protected ManufacturerController(
      ManufacturerService service, @Value("${manufacturerSearchFields}") String searchFields) {
    super(service, "manufacturer", searchFields);
  }

  @Override
  protected Manufacturer getInstanceForModel() {
    return new Manufacturer();
  }

  @Override
  protected void addAdditionalAttributes(Model model, boolean isDataErrorPresent) {
    model.addAttribute("exist", false);
    if (isDataErrorPresent) {
      Manufacturer manufacturer = ((Manufacturer) Objects.requireNonNull(model.getAttribute("newEntity")));
      Manufacturer manufacturerFromDB = ((ManufacturerService) service).getByName(manufacturer.getName());
      if (manufacturerFromDB != null) {
        model.addAttribute("exist", true);
      }
    }
  }
}
