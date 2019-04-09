package com.pau_pau.project.data.services;

import com.pau_pau.project.data.models.FilmDTO;
import com.pau_pau.project.data.models.api.EntityNotFoundException;

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

    FilmDTO findFilmById(int id) throws InstanceNotFoundException, EntityNotFoundException;

    FilmDTO addFilm(FilmDTO film);

    FilmDTO updateFilm(int id, FilmDTO film) throws InstanceAlreadyExistsException, InstanceNotFoundException, EntityNotFoundException;

    FilmDTO deleteFilmById(int id) throws InstanceNotFoundException, EntityNotFoundException;
}
