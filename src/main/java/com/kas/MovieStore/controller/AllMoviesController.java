package com.kas.MovieStore.controller;

import com.kas.MovieStore.entity.Movie;
import com.kas.MovieStore.entity.User;
import com.kas.MovieStore.entity.UserMovie;
import com.kas.MovieStore.sevice.MovieService;
import com.kas.MovieStore.sevice.UserMovieService;
import com.kas.MovieStore.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AllMoviesController {
    @Autowired
    MovieService movieService;

    @Autowired
    UserService userService;

    @Autowired
    UserMovieService userMovieService;

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
        User currentUser = (User) userService.loadUserByUsername((String) model.getAttribute("username"));
        Movie currentMovie = movieService.findByTitle(title);
        if (userMovieService.findUserMovie(currentUser, currentMovie) != null) {
            model.addAttribute("rateButtonName",
                    "Change mark (" + userMovieService.findUserMovie(currentUser, currentMovie).getMark() + ")");
        } else {
            model.addAttribute("rateButtonName", "Rate");
        }
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
        model.addAttribute("movie", movieService.findByTitle(title));
        return "edit";
    }

    @PostMapping("/allMovies/{title}/edit")
    public String movieEditPageProcess(@PathVariable(value = "title") String title,
                                       @ModelAttribute("movie") @Valid Movie editedMovie, Errors errors) {
        if (errors.hasErrors()) {
            return "edit";
        } else {
            movieService.updateMovie(movieService.findByTitle(title), editedMovie);
            return "movie";
        }
    }

    @GetMapping("/allMovies/{title}/rate")
    public String movieRatePage(@PathVariable(value = "title") String title, Model model) {
        model.addAttribute("movie", movieService.findByTitle(title));
        return "rate";
    }

    @PostMapping("/allMovies/{title}/rate")
    public String movieRatePageProcess(@PathVariable(value = "title") String title, @RequestParam int mark,
                                       Model model, Principal principal){
        User currentUser = (User) userService.loadUserByUsername((String)model.getAttribute("username"));
        Movie currentMovie = movieService.findByTitle(title);
        if (userMovieService.findUserMovie(currentUser, currentMovie) != null) {
            userMovieService.updateMark(currentUser, currentMovie, mark);
        } else {
            userMovieService.saveUserMovie(new UserMovie(currentUser, movieService.findByTitle(title), mark));
        }
        movieService.calculateRating(currentMovie);
        userService.calculateAvgMark(currentUser);
        return "redirect:/allMovies/{title}";
    }
}
