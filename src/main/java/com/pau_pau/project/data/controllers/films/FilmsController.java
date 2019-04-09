package com.pau_pau.project.data.controllers.films;

import com.pau_pau.project.models.films.FilmDTO;

import java.util.Date;
import java.util.List;

public interface FilmsController {

    List<FilmDTO> getFilms(String title,
                           Date year,
                           String country,
                           String genre,
                           Date releaseDate,
                           Float budget);

    FilmDTO getFilmById(int filmId);

    FilmDTO addFilm(FilmDTO film);

    FilmDTO updateFilm(int filmId, FilmDTO film);

    FilmDTO deleteFilm(int filmId);
}
