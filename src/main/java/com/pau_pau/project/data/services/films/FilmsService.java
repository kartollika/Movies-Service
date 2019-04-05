package com.pau_pau.project.data.services.films;

import com.pau_pau.project.data.models.films.FilmDTO;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Date;

public interface FilmsService {

    Iterable<FilmDTO> findFilms(String title,
                                Date year,
                                String country,
                                String genre,
                                Date releaseDate,
                                Float budget);

    FilmDTO findFilmById(int id) throws InstanceNotFoundException;

    FilmDTO addFilm(FilmDTO film);

    FilmDTO updateFilm(int id, FilmDTO film) throws InstanceAlreadyExistsException, InstanceNotFoundException;

    FilmDTO deleteFilmById(int id) throws InstanceNotFoundException;
}
