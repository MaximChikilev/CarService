package com.example.carservice.controllers;

import com.example.carservice.entity.Client;
import com.example.carservice.services.CarService;
import com.example.carservice.services.ClientService;
import com.example.carservice.services.UserService;
import com.example.carservice.services.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

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
  protected Client getInstanceForModel() {
    return new Client();
  }

  @Override
  protected void addAdditionalAttributes(Model model, boolean isDataErrorPresent) {
    model.addAttribute("cars", carService.getAll());
    model.addAttribute("existEmail", false);
    model.addAttribute("existPhoneNumber", false);
    model.addAttribute("incorrectEmail", false);
    model.addAttribute("existOwnerThisCar", false);
    if (isDataErrorPresent) {
      Client client = ((Client) Objects.requireNonNull(model.getAttribute("newEntity")));
      Client clientFromBD = ((ClientService) service).getClientByEmail(client.getEmail());
      if ((clientFromBD != null)
          && ((client.getClientId() == null) || (!isIdTheSame(client, clientFromBD))))
        model.addAttribute("existEmail", true);
      clientFromBD = ((ClientService) service).getClientByPhoneNumber(client.getPhoneNumber());
      if ((clientFromBD != null)
          && ((client.getClientId() == null) || (!isIdTheSame(client, clientFromBD))))
        model.addAttribute("existPhoneNumber", true);

      clientFromBD = carService.getClientByCar(client.getCar());
      if ((clientFromBD != null)
              && ((client.getClientId() == null) || (!isIdTheSame(client, clientFromBD))))
        model.addAttribute("existOwnerThisCar", true);


      if (!Utils.isEmailCorrect(client.getEmail())) model.addAttribute("incorrectEmail", true);
    }
  }

  @Override
  @PostMapping("/save")
  public String save(@ModelAttribute Client entity, Model model) {
    if (!isDataErrorPresent(entity)) {
      if (((ClientService) service).getClientByEmail(entity.getEmail()) == null) {
        userService.createNewCustomUserFromClientData(entity);
      }
      service.save(entity);
      return allRedirect;
    } else {
      addAttributes(model, entity, 0, true);
      return entityName;
    }
  }

  private boolean isIdTheSame(Client client1, Client client2) {
    return client1.getClientId().equals(client2.getClientId());
  }
}
