package com.example.carservice.controllers;

import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.entity.FindParams;
import com.example.carservice.services.EntityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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
  static String connectionsWithOtherEntitiesPage = "connectionWithOtherEntities";
  protected String returnUrl;
  protected String allRedirect;
  protected String searchFields;

  protected MyAbstractController(EntityService<T> service, String name, final String searchFields) {
    this.service = service;
    this.entityName = name;
    allRedirect = "redirect:/" + entityName + "/all";
    this.returnUrl = "/" + entityName + "/all";
    this.searchFields = searchFields;
  }

  @GetMapping("/all")
  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
  public String allEntities(
      Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
    addAttributes(model, getInstanceForModel(), page,false);
    return entityName;
  }

  @GetMapping("/delete")
  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
  public String delete(@RequestParam Long id, Model model) {
    List<ConnectionsWithOtherEntityDTO> connectionsWithOtherEntities =
        service.getConnectionsWithOtherTables(id);
    if (getConnectionsCount(connectionsWithOtherEntities) != 0L) {
      model.addAttribute("entities", connectionsWithOtherEntities);
      model.addAttribute("returnUrl", returnUrl);
      return connectionsWithOtherEntitiesPage;
    } else {
      service.delete(id);
      return allRedirect;
    }
  }

  @PostMapping("/save")
  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
  public String save(@ModelAttribute T entity,Model model) {
    Long id = service.getId(entity);
    if(!isDataErrorPresent(entity)){
      service.save(entity);
      return allRedirect;
    }else{
      addAttributes(model,entity,0,true);
      return entityName;
    }


  }

  @GetMapping("/edit")
  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
  public String edit(
      Model model,
      @RequestParam Long id,
      @RequestParam(required = false, defaultValue = "0") Integer page) {
    addAttributes(model, service.getById(id), page, false);
    return entityName;
  }

  @PostMapping("/find")
  @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
  public String find(
      @ModelAttribute FindParams findParams,
      Model model,
      @RequestParam(required = false, defaultValue = "0") Integer page) {
    addAttributes(model, getInstanceForModel(), page, false);
    List<T> list =
        service.getAllByFieldNameAndValue(findParams.getFieldName(), findParams.getValue());
    model.addAttribute("entities", list);
    return entityName;
  }

  protected void addAttributes(Model model, T entity, int page, boolean isDataErrorPresent) {
    if (page < 0) page = 0;
    List<T> entityList = service.getAll(PageRequest.of(page, ITEMS_PER_PAGE));
    model.addAttribute("allPages", getPageCount());
    model.addAttribute("entities", entityList);
    model.addAttribute("newEntity", entity);
    model.addAttribute("actionTitle", (service.getId(entity) == null) ? "New" : "Edit");
    model.addAttribute("findParams", new FindParams());
    model.addAttribute("fieldsList", getListPossibleSearchFields());
    addAdditionalAttributes(model, isDataErrorPresent);
  }

  protected boolean isDataErrorPresent(T entity){
    return service.isDataErrorPresent(entity);
  }

  private long getPageCount() {
    long totalCount = service.count();
    return (totalCount == 0)
        ? 1
        : (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
  }

  private Long getConnectionsCount(List<ConnectionsWithOtherEntityDTO> list) {
    Long counter = 0L;
    if (list==null) return counter;
    for (ConnectionsWithOtherEntityDTO element : list) {
      counter += element.getConnectionsCount();
    }
    return counter;
  }

  protected abstract T getInstanceForModel();

  protected abstract void addAdditionalAttributes(Model model, boolean isDataErrorPresent);

  protected List<String> getListPossibleSearchFields() {
    return new ArrayList<>(Arrays.asList(searchFields.split(",")));
  }
}
