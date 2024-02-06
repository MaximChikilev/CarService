package com.example.carservice.controllers;

import com.example.carservice.entity.CustomUser;
import com.example.carservice.entity.UserRole;
import com.example.carservice.services.UserService;
import com.example.carservice.services.Utils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {

  private final UserService userService;
  private final Utils utils;

  public MyController(UserService userService, Utils utils) {
    this.userService = userService;
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
    customUser.setRole(UserRole.ROLE_CLIENT);
    userService.save(customUser);
    return "redirect:/";
  }

  @GetMapping("/editPersonalData")
  public String editPersonalData(Model model) {
    CustomUser user = userService.getByLogin(Utils.getCurrentUser().getUsername());
    model.addAttribute("newUser", user);
    model.addAttribute("exist", true);
    return "registerNewUser";
  }

  @GetMapping("/home")
  public String home() {
    User currentUser = Utils.getCurrentUser();
    if (utils.isUserHasRole(currentUser, "ROLE_CLIENT")) return "redirect:/clientPage";
    return "redirect:/";
  }

  @GetMapping("/")
  public String getHome(Model model) {
    User currentUser = Utils.getCurrentUser();
    model.addAttribute("admin", utils.isUserHasRole(currentUser, "ROLE_ADMIN"));
    return "home";
  }

  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }
}
