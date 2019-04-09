package com.pau_pau.project.data.services;

import com.pau_pau.project.data.models.Film;
import com.pau_pau.project.data.models.FilmDTO;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Date;

public interface FilmsService {

    Iterable<Film> findFilms(String title,
                             Date year,
                             String country,
                             String genre,
                             Date releaseDate,
                             Float budget);

    Film findFilmById(int id) throws InstanceNotFoundException;

    Film addFilm(FilmDTO film);

    Film updateFilm(int id, FilmDTO film) throws InstanceAlreadyExistsException, InstanceNotFoundException;

    Film deleteFilmById(int id) throws InstanceNotFoundException;
}
