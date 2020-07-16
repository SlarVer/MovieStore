package com.kas.MovieStore.controller;

import com.kas.MovieStore.entity.User;
import com.kas.MovieStore.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;

    @ModelAttribute
    public void globalPageAttributes(Model model) {
        model.addAttribute(new User());
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute("user") @Valid User newUser, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(newUser);
        }
        return "redirect:login";
    }
}
