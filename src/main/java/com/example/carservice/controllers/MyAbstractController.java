package com.example.carservice.controllers;

import com.example.carservice.services.EntityService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public abstract class MyAbstractController<T> {
    protected final EntityService<T> service;
    protected String entityName;
    static final int ITEMS_PER_PAGE = 6;
    protected String allRedirect;

    protected MyAbstractController(EntityService<T> service,String name) {
        this.service = service;
        this.entityName = name;
        allRedirect = "redirect:/" + entityName + "/all";
    }
    @GetMapping("/all")
    public abstract String allEntities(Model model);
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
    public abstract String edit(Model model, @RequestParam Long id);
    protected void addAttributes(Model model, Long id) {
        model.addAttribute("allPages", getPageCount());
        model.addAttribute("entities", service.getAll());
        model.addAttribute("actionTitle", (id == 0) ? "New" : "Edit");
    }

    private long getPageCount() {
        long totalCount = service.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
