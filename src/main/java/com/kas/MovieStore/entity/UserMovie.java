package com.kas.MovieStore.entity;

import com.kas.MovieStore.storage.UserMovieId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users_movies")
public class UserMovie {
    @EmbeddedId
    private UserMovieId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    private Movie movie;

    private int mark;

    public UserMovieId getId() {
        return id;
    }

    public void setId(UserMovieId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public UserMovie() {}

    public UserMovie(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
        this.id = new UserMovieId(user.getId(), movie.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserMovie that = (UserMovie) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(movie, that.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, movie);
    }
}
