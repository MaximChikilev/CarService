package com.example.carservice.controllers;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Manufacturer;
import com.example.carservice.entity.SparePart;
import com.example.carservice.services.CarService;
import com.example.carservice.services.EntityService;
import com.example.carservice.services.SparePartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/sparePart")
public class SparePartController extends MyAbstractController<SparePart> {
  private final EntityService<Manufacturer> manufacturerEntityService;

  protected SparePartController(
      EntityService<SparePart> service,
      EntityService<Manufacturer> manufacturerEntityService,
      @Value("${sparePartSearchFields}") String searchFields) {
    super(service, "sparePart", searchFields);
    this.manufacturerEntityService = manufacturerEntityService;
  }

  @Override
  protected SparePart getInstanceForModel() {
    return new SparePart();
  }

  @Override
  protected void addAdditionalAttributes(Model model, boolean isDataErrorPresent) {
    model.addAttribute("manufacturers", manufacturerEntityService.getAll());
    model.addAttribute("exist", false);
    if (isDataErrorPresent) {
      SparePart sparePart = ((SparePart) Objects.requireNonNull(model.getAttribute("newEntity")));
      SparePart sparePartFromDB =
          ((SparePartService) service)
              .getByNamePartNumber(
                  sparePart.getName(),
                  sparePart.getPartNumber());
      if (sparePartFromDB != null) {
        model.addAttribute("exist", true);
      }
    }
  }
}
