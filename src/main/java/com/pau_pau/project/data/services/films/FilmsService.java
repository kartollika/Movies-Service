package com.pau_pau.project.data.services.films;

import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.films.FilmDTO;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Date;

public interface FilmsService {

    Iterable<Film> findFilms(String title,
                             Date year,
                             String country,
                             String genre,
                             Date releaseDate);

    Iterable<Film> findActiveRequests(String title,
                                      Date year,
                                      String country,
                                      String genre,
                                      Date releaseDate);

    Film findFilmById(int id) throws InstanceNotFoundException;

    Film addFilm(FilmDTO film) throws Exception;

    Film updateFilm(int id, FilmDTO film) throws InstanceAlreadyExistsException, InstanceNotFoundException;

    Film deleteFilmById(int id) throws InstanceNotFoundException;

    Film getRandomFilm();

    Film publishFilm(int id) throws Exception;

    Film rejectFilm(int id, String comment) throws Exception;
}
