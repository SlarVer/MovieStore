package com.kas.MovieStore.controller;

import com.kas.MovieStore.entity.Movie;
import com.kas.MovieStore.sevice.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AdminController {
    @Autowired
    MovieService movieService;

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/admin/add")
    public String addMovie(Model model) {
        model.addAttribute(new Movie());
        return "add";
    }

    @PostMapping("/admin/add")
    public String addMovieProcess(@ModelAttribute("movie") @Valid Movie newMovie, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "add";
        } else {
            if (movieService.saveMovie(newMovie)) {
                model.addAttribute("successAddMessage", "Movie successfully added");
            } else {
                model.addAttribute("errorAddMessage", "Movie with this title already exists");
            }
        }
        return "add";
    }
}
