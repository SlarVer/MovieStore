package com.kas.MovieStore.sevice;

import com.kas.MovieStore.entity.Movie;
import com.kas.MovieStore.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    public Movie findByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public boolean saveMovie(Movie movie) {
        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            return false;
        } else {
            movie.setNumberOfViews(0);
            movie.setRating(0.0);
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
}
