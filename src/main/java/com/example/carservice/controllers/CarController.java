package com.example.carservice.controllers;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Manufacturer;
import com.example.carservice.services.EntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/car")
public class CarController extends MyAbstractController<Car> {
    private final EntityService<Manufacturer> manufacturerEntityService;

    protected CarController(EntityService<Car> service, EntityService<Manufacturer> manufacturerEntityService) {
        super(service, "car");
        this.manufacturerEntityService = manufacturerEntityService;
    }
    @Override
    public String allEntities(Model model) {
        addAttributes(model, 0L);
        model.addAttribute("newEntity", new Car());
        model.addAttribute("manufacturers", manufacturerEntityService.getAll());
        return "car";
    }
    @Override
    public String edit(Model model, Long id) {
        addAttributes(model, id);
        model.addAttribute("newEntity", service.getById(id));
        model.addAttribute("manufacturers", manufacturerEntityService.getAll());
        return "car";
    }
}
