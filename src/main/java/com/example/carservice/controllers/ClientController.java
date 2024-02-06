package com.example.carservice.controllers;

import com.example.carservice.entity.Client;
import com.example.carservice.services.CarService;
import com.example.carservice.services.ClientService;
import com.example.carservice.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientController extends MyAbstractController<Client> {
  private final CarService carService;
  private final UserService userService;

  protected ClientController(
      ClientService service,
      CarService carService,
      UserService userService,
      @Value("${clientSearchFields}") String searchFields) {
    super(service, "client", searchFields);
    this.carService = carService;
    this.userService = userService;
  }

  @Override
  protected Client getNewInstance() {
    return new Client();
  }

  @Override
  protected void addAdditionalAttributes(Model model) {
    model.addAttribute("cars", carService.getAll());
  }

  @Override
  @PostMapping("/save")
  public String save(@ModelAttribute Client entity) {
    if (((ClientService) service).getClientByEmail(entity.getEmail()) == null) {
      userService.createNewCustomUserFromClientData(entity);
    }
    service.save(entity);
    return allRedirect;
  }
}
