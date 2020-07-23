package com.kas.MovieStore.sevice;

import com.kas.MovieStore.entity.Movie;
import com.kas.MovieStore.entity.User;
import com.kas.MovieStore.entity.UserMovie;
import com.kas.MovieStore.repository.MovieRepository;
import com.kas.MovieStore.repository.UserMovieRepository;
import com.kas.MovieStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserMovieService {
    @Autowired
    UserMovieRepository userMovieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Transactional
    public void saveUserMovie(UserMovie userMovie) {
        userMovieRepository.save(userMovie);
        userMovie.getMovie().increaseViews();
        userMovie.getUser().increaseMoviesWatched();
    }

    @Transactional
    public void updateMark(User user, Movie movie, int mark) {
        userMovieRepository.findByUserAndMovie(user, movie).setMark(mark);
    }

    public UserMovie findUserMovie(User user, Movie movie) {
        return userMovieRepository.findByUserAndMovie(user, movie);
    }


}
