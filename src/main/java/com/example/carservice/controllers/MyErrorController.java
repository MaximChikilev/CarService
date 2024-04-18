package com.example.carservice.controllers;

import com.example.carservice.services.Utils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {
  private final Utils utils;

  public MyErrorController(Utils utils) {
    this.utils = utils;
  }

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    String errorMessage;
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());
      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        return "error404";
      }
      if (statusCode == HttpStatus.BAD_REQUEST.value()) {
        return "error400";
      }
    }
    User currentUser = Utils.getCurrentUser();
    if (utils.isUserHasRole(currentUser, "ROLE_CLIENT")) {
      errorMessage = "Please, contact your manager";
    } else errorMessage = "Please, contact admin";
    model.addAttribute("errorMessage", errorMessage);
    return "errorPage";
  }

  public String getErrorPath() {
    return "/error";
  }
}
