package com.example.carservice.controllers;

import com.example.carservice.entity.Client;
import com.example.carservice.services.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController extends MyAbstractController<Client>{
    protected ClientController(ClientService service) {
        super(service, "client");
    }

    @Override
    protected Client getNewInstance() {
        return new Client();
    }

    @Override
    protected void addAdditionalAttributes(Model model) {

    }

    @Override
    protected List<String> getListPossibleSearchFields() {
        return new ArrayList<>(Arrays.asList("First name","Second name","Phone number","Address"));
    }
}
