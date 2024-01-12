package com.example.carservice.controllers;


import com.example.carservice.entity.Manufacturer;
import com.example.carservice.entity.SparePart;
import com.example.carservice.services.EntityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/sparePart")
public class SparePartController extends MyAbstractController<SparePart> {
    private final EntityService<Manufacturer> manufacturerEntityService;

    protected SparePartController(EntityService<SparePart> service, EntityService<Manufacturer> manufacturerEntityService) {
        super(service, "sparePart");
        this.manufacturerEntityService = manufacturerEntityService;
    }

    @Override
    protected SparePart getNewInstance() {
        return new SparePart();
    }

    @Override
    protected void addAdditionalAttributes(Model model) {
        model.addAttribute("manufacturers", manufacturerEntityService.getAll());
    }

    @Override
    protected List<String> getListPossibleSearchFields() {
        return new ArrayList<>(Arrays.asList("Part number","Name"));
    }
}
