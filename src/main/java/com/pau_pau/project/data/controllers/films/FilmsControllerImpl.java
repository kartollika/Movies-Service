package com.pau_pau.project.data.controllers.films;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.services.films.FilmsServiceImpl;
import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.films.FilmDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InstanceNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Movies", value = "Films", description = "Api for operations with films")
@RestController
@CrossOrigin
@RequestMapping(ControllerConstants.FILMS_URL)
public class FilmsControllerImpl implements FilmsController {

    @Autowired
    private FilmsServiceImpl filmsService;

    /* ================================
                 GET METHODS
     ================================== */

    @Override
    public List<FilmDTO> getFilms(String title, Date year, String country, String genre, Date releaseDate, Float budget) {
        if (year == null) {
            year = DEFAULT_FILM_YEAR.getTime();
        }
        if (releaseDate == null) {
            releaseDate = DEFAULT_FILM_RELEASE_DATE.getTime();
        }
        if (budget == null) {
            budget = DEFAULT_FILM_BUDGET;
        }

        List<Film> films = filmsService.findFilms(title, year, country, genre, releaseDate);
        return films.stream().map(FilmDTO::fromFilmModel).collect(Collectors.toList());
    }

    @Override
    public FilmDTO getFilmById(int filmId) {
        try {
            return FilmDTO.fromFilmModel(filmsService.findFilmById(filmId));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================
                 POST METHODS
     ================================== */

    @Override
    public FilmDTO addFilm(FilmDTO film) throws InstanceNotFoundException {
        return FilmDTO.fromFilmModel(filmsService.addFilm(film));
    }

    /* ================================
                 PUT METHODS
     ================================== */

    @Override
    public FilmDTO updateFilm(int filmId, FilmDTO film) {
        try {
            return FilmDTO.fromFilmModel(filmsService.updateFilm(filmId, film));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================7
                 DELETE METHODS
     ================================== */

    @Override
    public FilmDTO deleteFilm(int filmId) {
        try {
            return FilmDTO.fromFilmModel(filmsService.deleteFilmById(filmId));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
}
