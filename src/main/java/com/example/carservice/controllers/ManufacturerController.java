package com.example.carservice.controllers;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Manufacturer;
import com.example.carservice.services.EntityService;
import com.example.carservice.services.ManufacturerEntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController extends MyAbstractController<Manufacturer>{
    protected ManufacturerController(ManufacturerEntityService service) {
        super(service, "manufacturer");
    }

    @Override
    public String allEntities(Model model) {
        addAttributes(model, 0L);
        model.addAttribute("newEntity", new Manufacturer());
        return "manufacturer";
    }

    @Override
    public String edit(Model model, Long id) {
        addAttributes(model, id);
        model.addAttribute("newEntity", service.getById(id));
        return "manufacturer";
    }
}
