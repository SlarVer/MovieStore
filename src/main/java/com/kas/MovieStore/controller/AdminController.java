package com.kas.MovieStore.controller;

import antlr.collections.List;
import com.kas.MovieStore.entity.Movie;
import com.kas.MovieStore.sevice.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AdminController {
    @Autowired
    MovieService movieService;

    @ModelAttribute
    public void globalPageAttributes(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
    }

    @ModelAttribute("/admin/editChoice")
    public void globalEditChoicePageAttributes(Model model) {
        model.addAttribute("choiceList", movieService.getMoviesInChoice());
    }

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

    @GetMapping("/admin/editChoice")
    public String editChoicePage(Model model) {
        return "editChoice";
    }

    @PostMapping("/admin/{title}/removeFromChoice")
    public String removeFromAdminChoice(@PathVariable(value = "title") String title) {
        movieService.removeFromChoice(movieService.findByTitle(title));
        return "redirect:/admin/editChoice";
    }

    @PostMapping("/admin/addToChoice")
    public String addToAdminChoice(@RequestParam String title, Model model) {
        Movie movie = movieService.findByTitle(title);
        if (movie != null) {
            if (movie.isAdminChoice()) {
                model.addAttribute("movieAlreadyInChoiceError", "Movie is already in list");
            } else {
                movieService.addToChoice(movieService.findByTitle(title));
                globalEditChoicePageAttributes(model);
                model.addAttribute("successAddedToChoice", "Movie successfully added");
            }
        } else {
            model.addAttribute("movieNotFoundError", "Film not found");
        }
        return "editChoice";
    }
}
