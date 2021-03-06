package com.kas.MovieStore.sevice;

import com.kas.MovieStore.entity.Movie;
import com.kas.MovieStore.entity.UserMovie;
import com.kas.MovieStore.repository.MovieRepository;
import com.kas.MovieStore.repository.UserMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserMovieRepository userMovieRepository;

    public Movie findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public boolean saveMovie(Movie movie) {
        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            return false;
        } else {
            movieRepository.save(movie);
            return true;
        }
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Transactional
    public void updateMovie(Movie movie, Movie editedMovie) {
        movie.copy(editedMovie);
    }

    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
    }

    @Transactional
    public void calculateRating(Movie movie) {
        int sumRating = 0;
        for (UserMovie userMovie: userMovieRepository.getAllByMovie(movie)) {
            sumRating += userMovie.getMark();
        }
        movie.setRating(BigDecimal.valueOf((double) sumRating / movie.getUsers().size()).setScale(2, RoundingMode.HALF_UP));
    }

    @Transactional
    public void addToChoice(Movie movie) {
        movie.setAdminChoice(true);
    }

    @Transactional
    public void removeFromChoice(Movie movie) {
        movie.setAdminChoice(false);
    }

    public List<Movie> getMoviesInChoice() {
        return movieRepository.findAllByAdminChoiceTrue();
    }
}
