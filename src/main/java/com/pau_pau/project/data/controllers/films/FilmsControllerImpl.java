package com.pau_pau.project.data.controllers.films;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.models.FilmDTO;
import com.pau_pau.project.data.models.api.EntityNotFoundException;
import com.pau_pau.project.data.services.FilmsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Api(tags = "Movies", value = "Films", description = "Api for operations with films")
@RestController
@RequestMapping(ControllerConstants.FILMS_URL)
public class FilmsControllerImpl implements FilmsController {

    @Autowired
    private FilmsServiceImpl filmsService;

    private static final Calendar gregorianCalendarInitial = GregorianCalendar.getInstance();

    static {
        gregorianCalendarInitial.set(1900, Calendar.JANUARY, 0);
    }

    private static final String DEFAULT_FILM_TITLE = "";
    private static final Date DEFAULT_FILM_YEAR = new Date(0, 0, 0);
    private static final String DEFAULT_FILM_COUNTRY = "";
    private static final String DEFAULT_FILM_GENRE = "";
    private static final Date DEFAULT_FILM_RELEASE_DATE = new Date(0, 0, 0);
    private static final float DEFAULT_FILM_BUDGET = 0f;


    /* ================================
                 GET METHODS
     ================================== */

    @Override
    @ApiOperation(value = "Get list of Movies ", response = FilmDTO.class, responseContainer = "List")
    @GetMapping
    public List<FilmDTO> getFilms(@RequestParam(defaultValue = DEFAULT_FILM_TITLE) String title,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date year,
                                  @RequestParam(defaultValue = DEFAULT_FILM_COUNTRY) String country,
                                  @RequestParam(defaultValue = DEFAULT_FILM_GENRE) String genre,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate,
                                  @RequestParam(required = false) Float budget) {
        if (year == null) {
            year = DEFAULT_FILM_YEAR;
        }
        if (releaseDate == null) {
            releaseDate = DEFAULT_FILM_RELEASE_DATE;
        }
        if (budget == null) {
            budget = DEFAULT_FILM_BUDGET;
        }

        return filmsService.findFilms(title,
                year,
                country,
                genre,
                releaseDate,
                budget);
    }

    @Override
    @ApiOperation(value = "Get film by id ", response = FilmDTO.class)
    @GetMapping(ControllerConstants.FILM_BY_ID)
    public FilmDTO getFilmById(@RequestParam(name = "id") int filmId) throws EntityNotFoundException {
        return filmsService.findFilmById(filmId);
    }

    /* ================================
                 POST METHODS
     ================================== */

    @Override
    @ApiOperation(value = "Add new film", response = FilmDTO.class)
    @PostMapping(consumes = "application/json")
    public FilmDTO addFilm(@RequestBody FilmDTO film) {
        return filmsService.addFilm(film);
    }

    /* ================================
                 PUT METHODS
     ================================== */

    @Override
    @ApiOperation(value = "Update existing film", response = FilmDTO.class)
    @PutMapping(consumes = "application/json")
    public FilmDTO updateFilm(@RequestParam int filmId, @RequestBody FilmDTO film) throws EntityNotFoundException {
        return filmsService.updateFilm(filmId, film);
    }

    /* ================================
                 DELETE METHODS
     ================================== */

    @Override
    @ApiOperation(value = "Delete existing film", response = FilmDTO.class)
    @DeleteMapping
    public FilmDTO deleteFilm(@RequestParam int filmId) throws EntityNotFoundException {
        return filmsService.deleteFilmById(filmId);
    }
}
