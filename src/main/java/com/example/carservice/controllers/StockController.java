package com.example.carservice.controllers;

import com.example.carservice.entity.Stock;
import com.example.carservice.services.SparePartService;
import com.example.carservice.services.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stock")
public class StockController extends MyAbstractController<Stock> {

  private final SparePartService sparePartService;

  protected StockController(
      StockService service,
      SparePartService sparePartService,
      @Value("${stockSearchFields}") String searchFields) {
    super(service, "stock", searchFields);
    this.sparePartService = sparePartService;
  }

  @Override
  protected Stock getNewInstance() {
    return new Stock();
  }

  @Override
  protected void addAdditionalAttributes(Model model) {
    model.addAttribute("spareParts", sparePartService.getAll());
  }
}
