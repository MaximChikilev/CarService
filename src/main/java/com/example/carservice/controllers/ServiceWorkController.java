package com.example.carservice.controllers;

import com.example.carservice.entity.ServiceWork;
import com.example.carservice.entity.SparePart;
import com.example.carservice.services.EntityService;
import com.example.carservice.services.ServiceWorkService;
import com.example.carservice.services.SparePartService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/serviceWork")
public class ServiceWorkController extends MyAbstractController<ServiceWork> {
    private final SparePartService sparePartService;

    protected ServiceWorkController(ServiceWorkService service, SparePartService sparePartService) {
        super(service, "serviceWork");
        this.sparePartService = sparePartService;
    }

    @Override
    protected ServiceWork getNewInstance() {
        return new ServiceWork();
    }

    @Override
    protected void addAdditionalAttributes(Model model) {

    }

    @Override
    protected List<String> getListPossibleSearchFields() {
        return new ArrayList<>(Arrays.asList("Name", "Duration", "Spare part name"));
    }

    @GetMapping("/addSpareParts")
    public String getSparePartsToAdd(@RequestParam Long id, Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        addAttributesToPagesFroAddManyEntities(model,id,page);
        return "addSparePartsToServiceWork";
    }
    @GetMapping("/deleteSparePart")
    public String deleteSparePart(@RequestParam Long spId,
                                  @RequestParam Long swId,
                                  Model model){
        ((ServiceWorkService)service).deleteSparePartFromServiceWork(swId,spId);
        addAttributesToPagesFroAddManyEntities(model, swId,0);
        return "addSparePartsToServiceWork";
    }

    @PostMapping("/addSelectedSpareparts")
    public String addSelectedSpareparts(Model model,
            @RequestParam(value = "toDelete[]") Long[] toDelete,
            @RequestParam(value = "serviceWorkId") Long serviceWorkId) {
        ((ServiceWorkService)service).addSparePartsToServiceWork(toDelete,serviceWorkId);
        addAttributesToPagesFroAddManyEntities(model, serviceWorkId,0);
        return "addSparePartsToServiceWork";
    }

    private void addAttributesToPagesFroAddManyEntities(Model model, Long id, int page) {
        model.addAttribute("serviceWork", service.getById(id));
        model.addAttribute("allPages", getServicePartPageCount());
        List<SparePart> sparePartList = sparePartService.getAll(PageRequest.of(page, ITEMS_PER_PAGE));
        model.addAttribute("spareParts", sparePartList);
    }

    private long getServicePartPageCount() {
        long totalCount = sparePartService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
