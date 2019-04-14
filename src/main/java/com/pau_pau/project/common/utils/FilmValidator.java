package com.pau_pau.project.common.utils;

import com.pau_pau.project.models.films.FilmDTO;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class FilmValidator {

    private static final int LOWER_YEAR = 1895;
    private static int UPPER_YEAR;
    private static final int UPPER_YEAR_OFFSET = 5;
    private static final GregorianCalendar LOWER_TIME = new GregorianCalendar(LOWER_YEAR, Calendar.JANUARY, 0);
    private static GregorianCalendar UPPER_TIME;

    static {
        UPPER_YEAR = Calendar.getInstance().get(Calendar.YEAR) + UPPER_YEAR_OFFSET;
        UPPER_TIME = new GregorianCalendar(UPPER_YEAR, Calendar.JANUARY, 0);
    }

    public void validate(String title,
                         Date year,
                         String country,
                         String genre,
                         Date releaseDate,
                         Float budget) {

        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setTitle(title);
        filmDTO.setYear(year);
        filmDTO.setCountry(country);
        filmDTO.setGenre(genre);
        filmDTO.setRelease(releaseDate);
        filmDTO.setBudget(budget);

        validate(filmDTO);
    }

    public void validate(FilmDTO film) {
        if (film.getTitle().length() > 60) {
            throw new IllegalArgumentException("Parameter name has more than 60 symbols");
        }

        if (film.getYear().before(LOWER_TIME.getTime())) {
            throw new IllegalArgumentException("Parameter year was before " + LOWER_YEAR);
        }

        if (film.getYear().after(UPPER_TIME.getTime())) {
            throw new IllegalArgumentException("Parameter year will be after " + UPPER_YEAR);
        }

        if (film.getRelease().before(LOWER_TIME.getTime())) {
            throw new IllegalArgumentException("Parameter release was before " + LOWER_YEAR);
        }

        if (film.getRelease().after(UPPER_TIME.getTime())) {
            throw new IllegalArgumentException("Parameter release will be after " + UPPER_YEAR);
        }

        if (film.getBudget() < 0) {
            throw new IllegalArgumentException("Parameter budget is negative");
        }
    }
}
