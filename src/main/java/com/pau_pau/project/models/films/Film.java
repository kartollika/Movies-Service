package com.pau_pau.project.models.films;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pau_pau.project.models.directors.Director;
import com.pau_pau.project.models.directors.DirectorDTO;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "films")
public class Film {

    public static Film fromFilmDTOModel(FilmDTO filmDTO) {
        Film film = new Film();
        film.id = filmDTO.getId();
        film.title = filmDTO.getTitle();
        film.year = filmDTO.getYear();
        film.country = filmDTO.getCountry();
        for (DirectorDTO director : filmDTO.getDirectors()) {
            film.directors.add(Director.fromDirectorDTOModel(director));
        }
        film.genre = filmDTO.getGenre();
        film.budget = filmDTO.getBudget();
        film.release = filmDTO.getRelease();
        film.actors = filmDTO.getActors();
        film.description = filmDTO.getDescription();
        film.poster = filmDTO.getPoster();
        return film;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String title;

    @Column
    private Date year;

    @Column
    private String country;

    @Column
    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(
            name = "films_directors",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors = new HashSet<>();

    @Column
    private String genre;

    @Column(name = "release")
    private Date release;

    @Column
    private float budget;

    @Column
    private String poster;

    @Column
    private String actors;

    @Column
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
