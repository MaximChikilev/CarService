package com.example.carservice.controllers;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.Client;
import com.example.carservice.entity.CustomUser;
import com.example.carservice.entity.UserRole;
import com.example.carservice.services.CarService;
import com.example.carservice.services.UserService;
import com.example.carservice.services.Utils;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController extends MyAbstractController<CustomUser> {

  protected UserController(UserService service, @Value("${userSearchFields}") String searchFields) {
    super(service, "user", searchFields);
  }

  @Override
  protected CustomUser getInstanceForModel() {
    return new CustomUser();
  }

  @Override
  protected void addAdditionalAttributes(Model model, boolean isDataErrorPresent) {
    model.addAttribute("possibleRoles", getRolesList());
    model.addAttribute("exist", false);
    model.addAttribute("existEmail", false);
    model.addAttribute("incorrectEmail", false);
    if (isDataErrorPresent) {
      CustomUser customUser =
          ((CustomUser) Objects.requireNonNull(model.getAttribute("newEntity")));
      CustomUser customUserFromDB = (((UserService) service).findByLogin(customUser.getLogin()));
      if ((customUserFromDB != null)
              && ((customUser.getId() == null) || (!isIdTheSame(customUser, customUserFromDB))))
        model.addAttribute("exist", true);
      customUserFromDB = (((UserService) service).findByEmail(customUser.getEmail()));
      if ((customUserFromDB != null)
              && ((customUser.getId() == null) || (!isIdTheSame(customUser, customUserFromDB))))
        model.addAttribute("existEmail", true);
      if (!Utils.isEmailCorrect(customUser.getEmail())) model.addAttribute("incorrectEmail", true);
    }
  }

  private List<String> getRolesList() {
    return Arrays.stream(UserRole.values()).map(UserRole::name).collect(Collectors.toList());
  }
  private boolean isIdTheSame(CustomUser user1, CustomUser user2) {
    return user1.getId().equals(user2.getId());
  }
}
