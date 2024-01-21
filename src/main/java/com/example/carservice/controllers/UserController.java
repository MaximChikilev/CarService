package com.example.carservice.controllers;

import com.example.carservice.entity.User;
import com.example.carservice.entity.UserRole;
import com.example.carservice.repo.UserRepository;
import com.example.carservice.services.EntityService;
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
public class UserController extends MyAbstractController<User>{
    private final PasswordEncoder passwordEncoder;
    protected UserController(UserService service, PasswordEncoder passwordEncoder) {
        super(service, "user");
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/save")
    @Override
    public String save(@ModelAttribute User user) {
        if(!((UserService) service).isUserExist(user)){
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            service.save(user);
        }
        return allRedirect;
    }

    @Override
    protected User getNewInstance() {
        return new User();
    }

    @Override
    protected void addAdditionalAttributes(Model model) {
        model.addAttribute("possibleRoles", getRolesList());
    }

    @Override
    protected List<String> getListPossibleSearchFields() {
        return new ArrayList<>(Arrays.asList("Role"));
    }
    private List<String> getRolesList(){
        return  Arrays.stream(UserRole.values())
                .map(UserRole::name)
                .collect(Collectors.toList());
    }
}
