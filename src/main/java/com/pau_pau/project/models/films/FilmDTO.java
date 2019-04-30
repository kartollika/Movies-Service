package com.pau_pau.project.models.films;

import com.pau_pau.project.models.directors.Director;
import com.pau_pau.project.models.directors.DirectorDTO;
import com.pau_pau.project.models.states.FilmStateDTO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
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
            filmDTO.getDirectorsId().add(director.getId());
        }
        filmDTO.genre = film.getGenre();

        filmDTO.release = film.getRelease();
        filmDTO.setCreationDate(film.getCreationDate());
        filmDTO.state = FilmStateDTO.fromFilmModel(film.getState());
        filmDTO.actors = film.getActors();
        filmDTO.description = film.getDescription();
        filmDTO.poster = film.getPoster();
        return filmDTO;
    }

    @ApiModelProperty(readOnly = true)
    private int id;

    private String title;

    private Date year;

    private String country;

    private Set<DirectorDTO> directors = new HashSet<>();

    private Set<Integer> directorsId = new HashSet<>();

    private String genre;

    private Date release;

    private String poster;

    private String actors;

    private String description;


    @ApiModelProperty(readOnly = true)
    private Date creationDate;

    @ApiModelProperty(readOnly = true)
    private FilmStateDTO state;

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

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
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

    public FilmStateDTO getState() {
        return state;
    }

    public void setState(FilmStateDTO state) {
        this.state = state;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Integer> getDirectorsId() {
        return directorsId;
    }

    public void setDirectorsId(Set<Integer> directorsId) {
        this.directorsId = directorsId;
    }

}
