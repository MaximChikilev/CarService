package com.example.carservice;


import com.example.carservice.entity.*;
import com.example.carservice.jsonLoaders.*;
import com.example.carservice.services.EntityService;
import com.example.carservice.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
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
        model.addAttribute("newUser",new CustomUser());
        model.addAttribute("exist",false);
        return "registerNewUser";
    }
    @PostMapping("/saveNewUser")
    public String saveNewUser(@ModelAttribute CustomUser customUser) {
        if(userService.isUserExist(customUser)){
            CustomUser userFromDB = userService.getByLogin(customUser.getLogin());
            if (customUser.getPassword() != "") {
                userFromDB.setPassword(passwordEncoder.encode(customUser.getPassword()));
            }
            userFromDB.setEmail(customUser.getEmail());
            userService.save(userFromDB);
        }else {
            customUser.setRole(UserRole.ROLE_MANAGER);
            customUser.setPassword(passwordEncoder.encode(customUser.getPassword()));
            userService.save(customUser);
        }
        return "redirect:/";
    }
    @GetMapping("/editPersonalData")
    public String editPersonalData(Model model){
        CustomUser user = userService.getByLogin(getCurrentUser().getUsername());
        model.addAttribute("newUser",user);
        model.addAttribute("exist",true);
        return "registerNewUser";
    }

    @GetMapping("/")
    public String getHome(Model model) {
        loadAllEntityLists();
        User currentUser = getCurrentUser();
        model.addAttribute("admin", isAdmin(currentUser));
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
    private User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
    private boolean isAdmin(User user) {
        Collection<GrantedAuthority> roles = user.getAuthorities();

        for (GrantedAuthority auth : roles) {
            if ("ROLE_ADMIN".equals(auth.getAuthority()))
                return true;
        }

        return false;
    }
}
