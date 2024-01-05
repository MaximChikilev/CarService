package com.example.carservice;


import com.example.carservice.entity.*;
import com.example.carservice.jsonLoaders.*;
import com.example.carservice.services.EntityServiceOne;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@ComponentScan(basePackages = "com.example.carservice")
public class MyController {
    static final int ITEMS_PER_PAGE = 6;
    private final EntityServiceOne entityServiceOne;

    public MyController(EntityServiceOne entityServiceOne) {
        this.entityServiceOne = entityServiceOne;
    }

  /*  @GetMapping("/allCars")
    public String carsLoader(Model model) {
        model.addAttribute("allPages", getCarPageCount());
        model.addAttribute("cars", entityServiceOne.getCars());
        model.addAttribute("newCar", new Car());
        model.addAttribute("manufacturers", entityServiceOne.getManufacturers());
        model.addAttribute("actionTitle", "Add new car");
        return "car";
    }
    @GetMapping("/deleteCar")
    public String deleteCar(@RequestParam Long carId){
        entityServiceOne.deleteCar(carId);
        return "redirect:/allCars";
    }
    @GetMapping("/editCar")
    public String editCar(Model model, @RequestParam Long carId){
        model.addAttribute("allPages", getCarPageCount());
        model.addAttribute("cars", entityServiceOne.getCars());
        model.addAttribute("newCar", entityServiceOne.getCarById(carId));
        model.addAttribute("manufacturers", entityServiceOne.getManufacturers());
        model.addAttribute("actionTitle", "Edit car");
        return "car";
    } */

    @GetMapping("/")
    public String getHome() {
        loadAllEntityLists();
        return "home";
    }
   /* @PostMapping("/addCar")
    public String addCar(@ModelAttribute Car car) {
        entityServiceOne.saveCar(car);
        return "redirect:/allCars";
    }*/
   /* @GetMapping("/allManufacturers")
    public String allManufacturers(Model model) {
        model.addAttribute("manufacturers", entityServiceOne.getManufacturers());
        model.addAttribute("allPages", getManufacturerPageCount());
        model.addAttribute("newManufacturer", new Manufacturer());
        model.addAttribute("actionTitle", "Add new manufacturer");
        return "manufacturer";
    }
    @PostMapping("/saveManufacturer")
    public String saveManufacturer(@ModelAttribute Manufacturer manufacturer) {
        entityServiceOne.saveManufacturer(manufacturer);
        return "redirect:/allManufacturers";
    }

    @GetMapping("/editManufacturer")
    public String editManufacturer(Model model, @RequestParam Long manufacturerId){
        model.addAttribute("allPages", getManufacturerPageCount());
        model.addAttribute("manufacturers", entityServiceOne.getManufacturers());
        model.addAttribute("newManufacturer", entityServiceOne.getManufacturerById(manufacturerId));
        model.addAttribute("actionTitle", "Edit manufacturer");
        return "manufacturer";
    }
    @GetMapping("/deleteManufacturer")
    public String deleteManufacturer(@RequestParam Long manufacturerId){
        entityServiceOne.deleteManufacturer(manufacturerId);
        return "redirect:/allManufacturers";
    }*/
    @GetMapping("/allClients")
    public String allClients(Model model) {
        model.addAttribute("clients", entityServiceOne.getManufacturers());
        model.addAttribute("allPages", getManufacturerPageCount());
        model.addAttribute("newManufacturer", new Manufacturer());
        model.addAttribute("actionTitle", "Add new manufacturer");
        return "manufacturer";
    }

    private void loadAllEntityLists() {
        List<Car> carList = new ArrayList<>();
        List<Manufacturer> manufacturerList = new ArrayList<>();
        CarJsonFileManager carJsonFileListLoader = new CarJsonFileManager("src/main/resources/car.json");
        ManufacturerJsonFileManager manufacturerJsonFileManager = new ManufacturerJsonFileManager("src/main/resources/manufacturers.json");
        /*ClientJsonFileManager clientJsonFileManager = new ClientJsonFileManager("src/main/resources/clients.json");
        InspectionJsonFileManager inspectionJsonFileManager = new InspectionJsonFileManager("src/main/resources/inpections.json");
        SemitrailerJsonFileManager semitrailerJsonFileManager = new SemitrailerJsonFileManager("src/main/resources/semitrailer.json");
        ServiceWorkJsonFileManager serviceWorkJsonFileManager = new ServiceWorkJsonFileManager("src/main/resources/serviceWork.json");
        SparePartJsonFileManager sparePartJsonFileManager = new SparePartJsonFileManager("src/main/resources/sparepart.json");*/
        try {
            carList = carJsonFileListLoader.loadJsonListFromFile();
            manufacturerList = manufacturerJsonFileManager.loadJsonListFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        entityServiceOne.saveCars(carList);
        entityServiceOne.saveManufacturers(manufacturerList);
    }

    private long getCarPageCount() {
        long totalCount = entityServiceOne.carCount();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
    private long getManufacturerPageCount() {
        long totalCount = entityServiceOne.manufacturerCount();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
