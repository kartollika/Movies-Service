package com.pau_pau.project.data.controllers.films;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.models.Film;
import com.pau_pau.project.data.repository.films.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

@RestController
@RequestMapping(ControllerConstants.FILMS_URL)
public class FilmsControllerImpl {

    @Autowired
    private FilmsRepository filmsRepository;

    private static final Calendar gregorianCalendarInitial = GregorianCalendar.getInstance();

    static {
        gregorianCalendarInitial.set(1900, Calendar.JANUARY, 0);
    }

    private static final String DEFAULT_FILM_TITLE = "";
    private static final Timestamp DEFAULT_FILM_YEAR = new Timestamp(gregorianCalendarInitial.getTimeInMillis());
    private static final String DEFAULT_FILM_COUNTRY = "";
    private static final String DEFAULT_FILM_GENRE = "";
    private static final Timestamp DEFAULT_FILM_RELEASE_DATE = new Timestamp(gregorianCalendarInitial.getTimeInMillis());
    private static final float DEFAULT_FILM_BUDGET = 0f;


    /* ================================
                 GET METHODS
     ================================== */


    @GetMapping
    public Iterable<Film> getFilms(@RequestParam(defaultValue = DEFAULT_FILM_TITLE) String title,
                                   @RequestParam(required = false) Optional<Timestamp> year,
                                   @RequestParam(defaultValue = DEFAULT_FILM_COUNTRY) String country,
                                   @RequestParam(defaultValue = DEFAULT_FILM_GENRE) String genre,
                                   @RequestParam(required = false) Optional<Timestamp> releaseDate,
                                   @RequestParam(required = false) Optional<Float> budget) {
        return filmsRepository.findFilms(title,
                year.orElse(DEFAULT_FILM_YEAR),
                country,
                genre,
                releaseDate.orElse(DEFAULT_FILM_RELEASE_DATE),
                budget.orElse(DEFAULT_FILM_BUDGET));
    }

    @GetMapping(ControllerConstants.FILM_BY_ID)
    public Film getFilmById(@RequestParam int filmId) {
        return filmsRepository.findById(filmId).orElseThrow();
    }

    /* ================================
                 POST METHODS
     ================================== */

    @PostMapping
    public void addFilm(@ModelAttribute Film film) {
        filmsRepository.save(film);
    }


    /* ================================
                 PUT METHODS
     ================================== */

    @PutMapping
    public void updateFilm(@RequestParam int filmId, @ModelAttribute Film film) {
        film.setId(filmId);
        filmsRepository.save(film);
    }

    /* ================================
                 DELETE METHODS
     ================================== */

    @DeleteMapping
    public void deleteFilm(@RequestParam int filmId) {
        filmsRepository.deleteById(filmId);
    }


}
