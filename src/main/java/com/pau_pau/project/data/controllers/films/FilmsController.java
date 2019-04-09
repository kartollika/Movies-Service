package com.pau_pau.project.data.controllers.films;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.films.FilmDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.pau_pau.project.data.controllers.ControllerConstants.FILM_PATH_ID;

public interface FilmsController {

    String DEFAULT_FILM_TITLE = "";
    GregorianCalendar DEFAULT_FILM_YEAR = new GregorianCalendar(0, Calendar.JANUARY, 0);
    String DEFAULT_FILM_COUNTRY = "";
    String DEFAULT_FILM_GENRE = "";
    GregorianCalendar DEFAULT_FILM_RELEASE_DATE = new GregorianCalendar(0, Calendar.JANUARY, 0);
    float DEFAULT_FILM_BUDGET = 0f;

    @ApiOperation(value = "Get list of Movies ", response = FilmDTO.class, responseContainer = "List")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<FilmDTO> getFilms(@RequestParam(defaultValue = DEFAULT_FILM_TITLE) String title,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date year,
                           @RequestParam(defaultValue = DEFAULT_FILM_COUNTRY) String country,
                           @RequestParam(defaultValue = DEFAULT_FILM_GENRE) String genre,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate,
                           @RequestParam(required = false) Float budget);

    @ApiOperation(value = "Get film by id ", response = FilmDTO.class)
    @GetMapping(value = ControllerConstants.FILM_URL_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    FilmDTO getFilmById(@PathVariable(name = FILM_PATH_ID) int filmId);

    @ApiOperation(value = "Add new film", response = FilmDTO.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    FilmDTO addFilm(@RequestBody FilmDTO film);

    @ApiOperation(value = "Update existing film", response = FilmDTO.class)
    @PutMapping(value = ControllerConstants.FILM_URL_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    FilmDTO updateFilm(@PathVariable(name = FILM_PATH_ID) int filmId, @RequestBody FilmDTO film);

    @ApiOperation(value = "Delete existing film", response = FilmDTO.class)
    @DeleteMapping(value = ControllerConstants.FILM_URL_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    FilmDTO deleteFilm(@PathVariable(name = FILM_PATH_ID) int filmId);
}
