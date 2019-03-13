package com.pau_pau.project.data.models;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class FilmDTO {

    public static FilmDTO fromFilmModel(Film film) {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.id = film.getId();
        filmDTO.title = film.getTitle();
        filmDTO.year = film.getYear();
        filmDTO.country = film.getCountry();
        for (Director director : film.getDirectors()) {
            filmDTO.directors.add(DirectorDTO.fromDirectorModel(director));
        }
        filmDTO.genre = film.getGenre();
        filmDTO.budget = film.getBudget();
        filmDTO.release = film.getRelease();
        return filmDTO;
    }

    private int id;

    private String title;

    private Timestamp year;

    private String country;

    private Set<DirectorDTO> directors = new HashSet<>();

    private String genre;

    private Timestamp release;

    private float budget;

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

    public Timestamp getYear() {
        return year;
    }

    public void setYear(Timestamp year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<DirectorDTO> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<DirectorDTO> directors) {
        this.directors = directors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Timestamp getRelease() {
        return release;
    }

    public void setRelease(Timestamp release) {
        this.release = release;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

}
