package com.example.carservice.controllers;

import com.example.carservice.entity.CustomUser;
import com.example.carservice.entity.UserRole;
import com.example.carservice.services.UserService;
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
    private final PasswordEncoder passwordEncoder;

    protected UserController(UserService service, PasswordEncoder passwordEncoder) {
        super(service, "user");
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/save")
    @Override
    public String save(@ModelAttribute CustomUser customUser) {
        if (!((UserService) service).isUserExist(customUser)) {
            customUser.setPassword(passwordEncoder.encode(customUser.getPassword()));
            service.save(customUser);
        } else {
            CustomUser userFromDB = ((UserService) service).getByLogin(customUser.getLogin());
            if (customUser.getPassword() != "") {
                userFromDB.setPassword(passwordEncoder.encode(customUser.getPassword()));
            }
            userFromDB.setRole(customUser.getRole());
            userFromDB.setEmail(customUser.getEmail());
            service.save(userFromDB);
        }
        return allRedirect;
    }

    @Override
    protected CustomUser getNewInstance() {
        return new CustomUser();
    }

    @Override
    protected void addAdditionalAttributes(Model model) {
        model.addAttribute("possibleRoles", getRolesList());
    }

    @Override
    protected List<String> getListPossibleSearchFields() {
        return new ArrayList<>(Arrays.asList("Role"));
    }

    private List<String> getRolesList() {
        return Arrays.stream(UserRole.values())
                .map(UserRole::name)
                .collect(Collectors.toList());
    }
}
