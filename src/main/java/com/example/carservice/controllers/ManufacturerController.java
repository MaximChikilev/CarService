package com.example.carservice.controllers;

import com.example.carservice.entity.Manufacturer;
import com.example.carservice.services.ManufacturerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController extends MyAbstractController<Manufacturer> {
  protected ManufacturerController(
      ManufacturerService service, @Value("${manufacturerSearchFields}") String searchFields) {
    super(service, "manufacturer", searchFields);
  }

  @Override
  protected Manufacturer getNewInstance() {
    return new Manufacturer();
  }

  @Override
  protected void addAdditionalAttributes(Model model) {}
}
