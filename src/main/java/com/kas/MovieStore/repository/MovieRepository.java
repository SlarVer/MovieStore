package com.kas.MovieStore.repository;

import com.kas.MovieStore.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
    List<Movie> findAllByAdminChoiceTrue();
}
