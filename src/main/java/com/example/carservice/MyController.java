package com.example.carservice;


import com.example.carservice.entity.*;
import com.example.carservice.jsonLoaders.*;
import com.example.carservice.services.EntityService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@ComponentScan(basePackages = "com.example.carservice")
public class MyController {
    private final EntityService<Car> carService;
    private final EntityService<Manufacturer> manufacturerService;
    private final EntityService<Client> clientService;
    private final EntityService<SparePart> sparePartService;
    private final EntityService<ServiceWork> serviceWorkService;


    public MyController(EntityService<Car> carService, EntityService<Manufacturer> manufacturerService, EntityService<Client> clientService, EntityService<SparePart> sparePartService, EntityService<ServiceWork> serviceWorkService) {
        this.carService = carService;
        this.manufacturerService = manufacturerService;
        this.clientService = clientService;
        this.sparePartService = sparePartService;
        this.serviceWorkService = serviceWorkService;
    }


    @GetMapping("/")
    public String getHome() {
        loadAllEntityLists();
        return "home";
    }

    private void loadAllEntityLists() {
        List<Car> carList = new ArrayList<>();
        List<Manufacturer> manufacturerList = new ArrayList<>();
        List<Client> clientList = new ArrayList<>();
        List<SparePart> sparePartList = new ArrayList<>();
        List<ServiceWork> serviceWorkList = new ArrayList<>();
        CarJsonFileManager carJsonFileListLoader = new CarJsonFileManager("src/main/resources/car.json");
        ManufacturerJsonFileManager manufacturerJsonFileManager = new ManufacturerJsonFileManager("src/main/resources/manufacturers.json");
        ClientJsonFileManager clientJsonFileManager = new ClientJsonFileManager("src/main/resources/clients.json");
        //InspectionJsonFileManager inspectionJsonFileManager = new InspectionJsonFileManager("src/main/resources/inpections.json");
        ServiceWorkJsonFileManager serviceWorkJsonFileManager = new ServiceWorkJsonFileManager("src/main/resources/serviceWork.json");
        SparePartJsonFileManager sparePartJsonFileManager = new SparePartJsonFileManager("src/main/resources/sparepart.json");
        try {
            carList = carJsonFileListLoader.loadJsonListFromFile();
            manufacturerList = manufacturerJsonFileManager.loadJsonListFromFile();
            clientList = clientJsonFileManager.loadJsonListFromFile();
            sparePartList = sparePartJsonFileManager.loadJsonListFromFile();
            serviceWorkList = serviceWorkJsonFileManager.loadJsonListFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        carService.saveAll(carList);
        manufacturerService.saveAll(manufacturerList);
        clientService.saveAll(clientList);
        sparePartService.saveAll(sparePartList);
        serviceWorkService.saveAll(serviceWorkList);
    }
}
