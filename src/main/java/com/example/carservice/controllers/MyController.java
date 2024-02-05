package com.example.carservice.controllers;


import com.example.carservice.entity.*;
import com.example.carservice.jsonLoaders.deserializer.GpsTrackerDataDeserializer;
import com.example.carservice.services.UserService;
import com.example.carservice.services.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class MyController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Utils utils;


    public MyController(UserService userService, PasswordEncoder passwordEncoder, Utils utils) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
    }

    @GetMapping("/register")
    public String registerNewUser(Model model) {
        model.addAttribute("newUser", new CustomUser());
        model.addAttribute("exist", false);
        return "registerNewUser";
    }

    @PostMapping("/saveNewUser")
    public String saveNewUser(@ModelAttribute CustomUser customUser) {
        if (userService.isUserExist(customUser)) {
            CustomUser userFromDB = userService.getByLogin(customUser.getLogin());
            if (customUser.getPassword() != "") {
                userFromDB.setPassword(passwordEncoder.encode(customUser.getPassword()));
            }
            userFromDB.setEmail(customUser.getEmail());
            userService.save(userFromDB);
        } else {
            customUser.setRole(UserRole.ROLE_CLIENT);
            customUser.setPassword(passwordEncoder.encode(customUser.getPassword()));
            userService.save(customUser);
        }
        return "redirect:/";
    }

    @GetMapping("/editPersonalData")
    public String editPersonalData(Model model) {
        CustomUser user = userService.getByLogin(utils.getCurrentUser().getUsername());
        model.addAttribute("newUser", user);
        model.addAttribute("exist", true);
        return "registerNewUser";
    }

    @GetMapping("/home")
    public String home() {
        User currentUser = utils.getCurrentUser();
        if (utils.isUserHasRole(currentUser, "ROLE_CLIENT")) return "redirect:/clientPage";
        return "redirect:/";
    }

    @GetMapping("/")
    public String getHome(Model model) {
        User currentUser = utils.getCurrentUser();
        if (utils.isUserHasRole(currentUser, "ROLE_CLIENT")) return "clientPage";
        model.addAttribute("admin", utils.isUserHasRole(currentUser, "ROLE_ADMIN"));
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
