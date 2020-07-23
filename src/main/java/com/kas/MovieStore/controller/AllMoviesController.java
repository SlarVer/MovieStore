package com.kas.MovieStore.controller;

import com.kas.MovieStore.entity.Movie;
import com.kas.MovieStore.repository.MovieRepository;
import com.kas.MovieStore.sevice.MovieService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AllMoviesController {
    @Autowired
    MovieService movieService;

    @ModelAttribute
    public void globalPageAttributes(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("allMoviesList", movieService.getAllMovies());
    }

    @GetMapping("/allMovies")
    public String displayAllMovies() {
        return "allMovies";
    }

    @GetMapping("/allMovies/{title}")
    public String moviePage(@PathVariable(value = "title") String title, Model model) {
        Movie movie = movieService.findByTitle(title);
        model.addAttribute("movie", movie);
        return "movie";
    }
    
    @PostMapping("/allMovies/{title}")
    public String deleteMovie(@PathVariable(value = "title") String title) {
        movieService.deleteMovie(movieService.findByTitle(title));
        return "redirect:/allMovies";
    }

    @GetMapping("/allMovies/{title}/edit")
    public String movieEditPage(@PathVariable(value = "title") String title, Model model) {
        Movie movie = movieService.findByTitle(title);
        model.addAttribute("movie", movie);
        return "edit";
    }

    @PostMapping("/allMovies/{title}/edit")
    public String movieEditPageProcess(@PathVariable(value = "title") String title,
                                       @ModelAttribute("movie") @Valid Movie editedMovie, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "edit";
        } else {
            movieService.updateMovie(movieService.findByTitle(title), editedMovie);
            return "movie";
        }
    }
}
