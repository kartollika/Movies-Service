package com.pau_pau.project.data.controllers.films;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.films.FilmDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.pau_pau.project.data.controllers.ControllerConstants.*;

public interface FilmsController {

    String DEFAULT_FILM_TITLE = "";
    GregorianCalendar DEFAULT_FILM_YEAR = new GregorianCalendar(0, Calendar.JANUARY, 0);
    String DEFAULT_FILM_COUNTRY = "";
    String DEFAULT_FILM_GENRE = "";
    GregorianCalendar DEFAULT_FILM_RELEASE_DATE = new GregorianCalendar(0, Calendar.JANUARY, 0);
    float DEFAULT_FILM_BUDGET = 0f;

    @ApiOperation(value = "Get list of Movies. " + AVAILABLE_EVERYONE, response = FilmDTO.class, responseContainer = "List")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<FilmDTO> getFilms(@RequestParam(defaultValue = DEFAULT_FILM_TITLE) String title,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date year,
                           @RequestParam(defaultValue = DEFAULT_FILM_COUNTRY) String country,
                           @RequestParam(defaultValue = DEFAULT_FILM_GENRE) String genre,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate);

    @ApiOperation(value = "Get film by id. Available for everyone. ", response = FilmDTO.class, authorizations = @Authorization(value = "Bearer"))
    @GetMapping(value = ControllerConstants.FILM_URL_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    FilmDTO getFilmById(@PathVariable(name = FILM_PATH_ID) int filmId);

    @ApiOperation(value = "Add new film. " + AVAILABLE_EDITOR_ADMIN, response = FilmDTO.class, authorizations = @Authorization(value = "Bearer"))
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.CREATED)
    FilmDTO addFilm(@RequestBody FilmDTO film) throws Exception;

    @ApiOperation(value = "Update existing film. " + AVAILABLE_EDITOR_ADMIN, response = FilmDTO.class, authorizations = @Authorization(value = "Bearer"))
    @PutMapping(value = ControllerConstants.FILM_URL_BY_ID)
    @Secured({"ROLE_ADMIN", "ROLE_EDITOR"})
    @ResponseStatus(HttpStatus.OK)
    FilmDTO updateFilm(@PathVariable(name = FILM_PATH_ID) int filmId, @RequestBody FilmDTO film);

    @ApiOperation(value = "Delete existing film. " + AVAILABLE_ADMIN, response = FilmDTO.class, authorizations = @Authorization(value = "Bearer"))
    @DeleteMapping(value = ControllerConstants.FILM_URL_BY_ID)
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    FilmDTO deleteFilm(@PathVariable(name = FILM_PATH_ID) int filmId);

    @ApiOperation(value = "Get random film", response = FilmDTO.class)
    @GetMapping(value = ControllerConstants.FILM_RANDOM)
    FilmDTO getRandomFilm();

    @ApiOperation(value = "Publish the film by ID", response = FilmDTO.class, authorizations = @Authorization(value = "Bearer"))
    @PutMapping(value = ControllerConstants.FILM_PUBLISH)
    @ResponseStatus(HttpStatus.OK)
    FilmDTO publishFilm(@PathVariable(name = FILM_PATH_ID) int filmId) throws Exception;

    @ApiOperation(value = "Rejects the film to be added to common list", response = FilmDTO.class, authorizations = @Authorization(value = "Bearer"))
    @PutMapping(value = ControllerConstants.FILM_REJECT)
    @ResponseStatus(HttpStatus.OK)
    FilmDTO rejectFilm(@PathVariable(name = FILM_PATH_ID) int filmId, @RequestParam(name = "comment", required = false) String comment) throws Exception;


}
