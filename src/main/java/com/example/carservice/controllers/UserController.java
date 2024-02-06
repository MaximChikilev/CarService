package com.example.carservice.controllers;

import com.example.carservice.entity.CustomUser;
import com.example.carservice.entity.UserRole;
import com.example.carservice.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController extends MyAbstractController<CustomUser> {

  protected UserController(UserService service, @Value("${userSearchFields}") String searchFields) {
    super(service, "user", searchFields);
  }

  @Override
  protected CustomUser getNewInstance() {
    return new CustomUser();
  }

  @Override
  protected void addAdditionalAttributes(Model model) {
    model.addAttribute("possibleRoles", getRolesList());
  }

  private List<String> getRolesList() {
    return Arrays.stream(UserRole.values()).map(UserRole::name).collect(Collectors.toList());
  }
}
