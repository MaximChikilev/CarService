package com.example.carservice.controllers;

import com.example.carservice.entity.Stock;
import com.example.carservice.services.SparePartService;
import com.example.carservice.services.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Controller
@RequestMapping("/stock")
public class StockController extends MyAbstractController<Stock>{

    private final SparePartService sparePartService;
    protected StockController(StockService service, SparePartService sparePartService) {
        super(service, "stock");
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

    @Override
    protected List<String> getListPossibleSearchFields() {
        return new ArrayList<>(Arrays.asList("Spare part name"));
    }
}
