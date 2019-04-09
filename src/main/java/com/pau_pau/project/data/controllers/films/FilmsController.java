package com.pau_pau.project.data.controllers.films;

import com.pau_pau.project.data.models.FilmDTO;
import com.pau_pau.project.data.models.api.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface FilmsController {

    List<FilmDTO> getFilms(String title,
                           Date year,
                           String country,
                           String genre,
                           Date releaseDate,
                           Float budget);

    FilmDTO getFilmById(int filmId) throws EntityNotFoundException;

    FilmDTO addFilm(FilmDTO film);

    FilmDTO updateFilm(int filmId, FilmDTO film) throws EntityNotFoundException;

    FilmDTO deleteFilm(int filmId) throws EntityNotFoundException;
}
