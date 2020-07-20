package com.kas.MovieStore.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1)
    private String title;

    @NotNull
    @Size(min = 1)
    private String slogan;

    @NotNull
    private int length;

    @NotNull
    @Min(value = 0)
    private int numberOfViews;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private double rating;

    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserMovie> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Movie() {
    }

    public Movie(@Size(min = 1) String title, @Size(min = 1) String slogan, int length, @Min(value = 0) int numberOfViews, @Min(value = 0) @Max(value = 10) double rating) {
        this.title = title;
        this.slogan = slogan;
        this.length = length;
        this.numberOfViews = numberOfViews;
        this.rating = rating;
    }
}
