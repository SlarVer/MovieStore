package com.kas.MovieStore.repository;

import com.kas.MovieStore.entity.Movie;
import com.kas.MovieStore.entity.User;
import com.kas.MovieStore.entity.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMovieRepository extends JpaRepository<UserMovie, Long> {
    UserMovie findByUserAndMovie(User user, Movie movie);
    int countAllByMovie(Movie movie);
    List<UserMovie> getAllByMovie(Movie movie);
    List<UserMovie> getAllByUser(User user);
}
