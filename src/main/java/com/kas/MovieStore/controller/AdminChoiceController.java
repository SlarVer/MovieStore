package com.kas.MovieStore.controller;

import com.kas.MovieStore.sevice.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AdminChoiceController {
    @Autowired
    MovieService movieService;

    @ModelAttribute
    public void globalPageAttributes(Model model) {
        model.addAttribute("chosenMovies", movieService.getMoviesInChoice());
    }

    @GetMapping("/choice")
    public String adminChoicePage() {
        return "choice";
    }
}
