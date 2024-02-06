package com.example.carservice.controllers;

import com.example.carservice.entity.ServiceWork;
import com.example.carservice.entity.TechnicalInspection;
import com.example.carservice.services.InspectionService;
import com.example.carservice.services.ServiceWorkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/technicalInspection")
public class InspectionController extends MyAbstractController<TechnicalInspection> {
  private final ServiceWorkService serviceWorkService;

  protected InspectionController(
      InspectionService service,
      ServiceWorkService serviceWorkService,
      @Value("${inspectionSearchFields}") String searchFields) {
    super(service, "technicalInspection", searchFields);
    this.serviceWorkService = serviceWorkService;
  }

  @Override
  protected TechnicalInspection getNewInstance() {
    return new TechnicalInspection();
  }

  @Override
  protected void addAdditionalAttributes(Model model) {}

  @GetMapping("/addServiceWorks")
  public String getSparePartsToAdd(
      @RequestParam Long id,
      Model model,
      @RequestParam(required = false, defaultValue = "0") Integer page) {
    addAttributesToPagesFroAddManyEntities(model, id, page);
    return "addServiceWorksToTechnicalInspections";
  }

  @GetMapping("/deleteServiceWork")
  public String deleteSparePart(
      @RequestParam Long inspectionId, @RequestParam Long swId, Model model) {
    ((InspectionService) service).deleteServiceWorkFromInspection(swId, inspectionId);
    addAttributesToPagesFroAddManyEntities(model, inspectionId, 0);
    return "addServiceWorksToTechnicalInspections";
  }

  @PostMapping("/addSelectedServiceWorks")
  public String addSelectedSpareparts(
      Model model,
      @RequestParam(value = "toDelete[]") Long[] toDelete,
      @RequestParam(value = "inspectionsId") Long inspectionsId) {
    ((InspectionService) service).addServiceWorkToInspection(toDelete, inspectionsId);
    addAttributesToPagesFroAddManyEntities(model, inspectionsId, 0);
    return "addServiceWorksToTechnicalInspections";
  }

  private void addAttributesToPagesFroAddManyEntities(Model model, Long id, int page) {
    model.addAttribute("inspection", service.getById(id));
    model.addAttribute("allPages", getServiceWorkPageCount());
    List<ServiceWork> serviceWorkList =
        serviceWorkService.getAll(PageRequest.of(page, ITEMS_PER_PAGE));
    model.addAttribute("serviceWorks", serviceWorkList);
  }

  private long getServiceWorkPageCount() {
    long totalCount = serviceWorkService.count();
    return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
  }
}
