package com.example.carservice.controllers;

import com.example.carservice.entity.FindParams;
import com.example.carservice.services.EntityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MyAbstractController<T> {
  protected final EntityService<T> service;
  protected String entityName;
  static final int ITEMS_PER_PAGE = 6;
  protected String allRedirect;
  protected String searchFields;

  protected MyAbstractController(EntityService<T> service, String name, final String searchFields) {
    this.service = service;
    this.entityName = name;
    allRedirect = "redirect:/" + entityName + "/all";
    this.searchFields = searchFields;
  }

  @GetMapping("/all")
  public String allEntities(
      Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
    addAttributes(model, 0L, page);
    return entityName;
  }

  @GetMapping("/delete")
  public String delete(@RequestParam Long id) {
    service.delete(id);
    return allRedirect;
  }

  @PostMapping("/save")
  public String save(@ModelAttribute T entity) {
    service.save(entity);
    return allRedirect;
  }

  @GetMapping("/edit")
  public String edit(
      Model model,
      @RequestParam Long id,
      @RequestParam(required = false, defaultValue = "0") Integer page) {
    addAttributes(model, id, page);
    return entityName;
  }

  @PostMapping("/find")
  public String find(
      @ModelAttribute FindParams findParams,
      Model model,
      @RequestParam(required = false, defaultValue = "0") Integer page) {
    addAttributes(model, 0L, page);
    List<T> list =
        service.getAllByFieldNameAndValue(findParams.getFieldName(), findParams.getValue());
    model.addAttribute("entities", list);
    return entityName;
  }

  protected void addAttributes(Model model, Long id, int page) {
    if (page < 0) page = 0;
    List<T> entityList = service.getAll(PageRequest.of(page, ITEMS_PER_PAGE));
    model.addAttribute("allPages", getPageCount());
    model.addAttribute("entities", entityList);
    model.addAttribute("newEntity", (id == 0) ? getNewInstance() : service.getById(id));
    model.addAttribute("actionTitle", (id == 0) ? "New" : "Edit");
    model.addAttribute("findParams", new FindParams());
    model.addAttribute("fieldsList", getListPossibleSearchFields());
    addAdditionalAttributes(model);
  }

  private long getPageCount() {
    long totalCount = service.count();
    return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
  }

  protected abstract T getNewInstance();

  protected abstract void addAdditionalAttributes(Model model);

  protected List<String> getListPossibleSearchFields() {
    return new ArrayList<>(Arrays.asList(searchFields.split(",")));
  }
}
