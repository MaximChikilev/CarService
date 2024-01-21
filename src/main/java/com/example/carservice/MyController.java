package com.example.carservice;


import com.example.carservice.entity.*;
import com.example.carservice.jsonLoaders.*;
import com.example.carservice.services.EntityService;
import com.example.carservice.services.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    private final EntityService<TechnicalInspection> technicalInspectionService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public MyController(EntityService<Car> carService, EntityService<Manufacturer> manufacturerService, EntityService<Client> clientService, EntityService<SparePart> sparePartService, EntityService<ServiceWork> serviceWorkService, EntityService<TechnicalInspection> technicalInspectionService, UserService userService, PasswordEncoder passwordEncoder) {
        this.carService = carService;
        this.manufacturerService = manufacturerService;
        this.clientService = clientService;
        this.sparePartService = sparePartService;
        this.serviceWorkService = serviceWorkService;
        this.technicalInspectionService = technicalInspectionService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerNewUser(Model model) {
        model.addAttribute("newUser",new User());
        model.addAttribute("newUser",null);
        return "registerNewUser";
    }
    @GetMapping("/saveNewUser")
    public String saveNewUser(Model model, @ModelAttribute User user) {
        if(userService.isUserExist(user)){
            model.addAttribute("newUser",new User());
            model.addAttribute("exists",true);
            return "registerNewUser";
        }
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getHome(Model model) {
        loadAllEntityLists();
        model.addAttribute("admin", true);
        return "home";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    private void loadAllEntityLists() {
        List<Car> carList = new ArrayList<>();
        List<Manufacturer> manufacturerList = new ArrayList<>();
        List<Client> clientList = new ArrayList<>();
        List<SparePart> sparePartList = new ArrayList<>();
        List<ServiceWork> serviceWorkList = new ArrayList<>();
        List<TechnicalInspection> inspectionsList = new ArrayList<>();
        CarJsonFileManager carJsonFileListLoader = new CarJsonFileManager("src/main/resources/car.json");
        ManufacturerJsonFileManager manufacturerJsonFileManager = new ManufacturerJsonFileManager("src/main/resources/manufacturers.json");
        ClientJsonFileManager clientJsonFileManager = new ClientJsonFileManager("src/main/resources/clients.json");
        InspectionJsonFileManager inspectionJsonFileManager = new InspectionJsonFileManager("src/main/resources/inpections.json");
        ServiceWorkJsonFileManager serviceWorkJsonFileManager = new ServiceWorkJsonFileManager("src/main/resources/serviceWork.json");
        SparePartJsonFileManager sparePartJsonFileManager = new SparePartJsonFileManager("src/main/resources/sparepart.json");
        try {
            carList = carJsonFileListLoader.loadJsonListFromFile();
            manufacturerList = manufacturerJsonFileManager.loadJsonListFromFile();
            clientList = clientJsonFileManager.loadJsonListFromFile();
            sparePartList = sparePartJsonFileManager.loadJsonListFromFile();
            serviceWorkList = serviceWorkJsonFileManager.loadJsonListFromFile();
            inspectionsList = inspectionJsonFileManager.loadJsonListFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        carService.saveAll(carList);
        manufacturerService.saveAll(manufacturerList);
        clientService.saveAll(clientList);
        sparePartService.saveAll(sparePartList);
        serviceWorkService.saveAll(serviceWorkList);
        technicalInspectionService.saveAll(inspectionsList);
    }
}
