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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ProfileController {
    @Autowired
    UserService userService;

    @ModelAttribute
    public void globalPageAttributes(Model model, Principal principal) {
        model.addAttribute("currentUser", userService.loadUserByUsername(principal.getName()));
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

//    @GetMapping("/profile/picture")
//    public String pictureChange(Model model) {
//        model.addAttribute("choice");
//        return "picture";
//    }
//
//    @PostMapping("/profile/picture")
//    public String pictureChangeAction(@RequestParam String choice) {
//
//    }

    @GetMapping("/profile/password")
    public String passwordChange(Model model) {
        model.addAttribute(new User());
        return "password";
    }

//    @PostMapping("/profile/picture")
//    public String passwordChangeAction(@RequestParam String currentPassword, @RequestParam String newPassword,
//                                       Model model, Principal principal) {
//        if (userService.checkPassword(principal.getName(), currentPassword)) {
//            model.addAttribute("passwordCheckError", "Incorrect current password");
//            return "password";
//        }
//        return "meh";
//    }

    @PostMapping("/profile/password")
    public String passwordChangeAction(@RequestParam String currentPassword, @RequestParam String repeatNewPassword,
                                       @ModelAttribute("user") @Valid User tempUser, Errors errors, Model model, Principal principal) {
        if (!userService.checkPassword(principal.getName(), currentPassword)) {
            model.addAttribute("passwordCheckError", "Incorrect current password");
            return "password";
        }
        if (!tempUser.getPassword().equals(repeatNewPassword)) {
            model.addAttribute("passwordRepeatError", "Passwords don't match");
            return "password";
        }
        if (tempUser.getPassword().equals(currentPassword)) {
            model.addAttribute("passwordMatchError", "New password must be different from the current one");
            return "password";
        }
        if (errors.hasErrors()) {
            return "password";
        }
        userService.updatePassword(principal.getName(), tempUser.getPassword());
        model.addAttribute("passwordUpdateSuccess", "Password cuccessfully updated");
        return "password";
    }


}
