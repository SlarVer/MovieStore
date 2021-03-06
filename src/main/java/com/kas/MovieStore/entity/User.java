package com.kas.MovieStore.entity;

import com.kas.MovieStore.validation.ValidPassword;
import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6, max = 15, message = "Length must be between 6 and 15 characters")
    private String username;

    @ValidPassword             //Custom annotation
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @NotNull
    @Size(min = 2, max = 40, message = "Length must be between 2 and 40 characters")
    private String name;

    @NotNull
    private Date birthDate;

    @NotNull
    @Min(value = 0)
    private int moviesWatched;

    private BigDecimal avgMark;

    private String profilePic;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserMovie> movies = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getRolesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Role role: getRoles()){
            stringBuilder.append(new StringBuilder(role.getName()).delete(0, 5)).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getMoviesWatched() {
        return moviesWatched;
    }

    public void setMoviesWatched(int moviesWatched) {
        this.moviesWatched = moviesWatched;
    }

    public BigDecimal getAvgMark() {
        return avgMark;
    }

    public void setAvgMark(BigDecimal avgMark) {
        this.avgMark = avgMark;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public List<UserMovie> getMovies() {
        return movies;
    }

    public void setMovies(List<UserMovie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public User(){
    }

    public User(String username, String password, String name, Date birthDate) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
    }

    public void increaseMoviesWatched() {
        this.moviesWatched++;
    }
}
