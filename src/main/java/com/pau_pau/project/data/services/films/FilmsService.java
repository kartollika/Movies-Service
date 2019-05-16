package com.pau_pau.project.data.services.films;

import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.films.FilmDTO;

import javax.management.InstanceNotFoundException;
import java.util.Date;
import java.util.List;

public interface FilmsService {

    List<Film> findFilms(String title,
                         Date year,
                         String country,
                         String genre,
                         Date releaseDate);

    List<Film> findActiveRequests();

    List<Film> findActiveRequestsForAccount(int accountId);

    Film findFilmById(int id) throws InstanceNotFoundException;

    Film addFilm(FilmDTO film) throws Exception;

    Film updateFilm(int id, FilmDTO film) throws Exception;

    Film deleteFilmById(int id) throws InstanceNotFoundException;

    Film getRandomFilm();

    Film publishFilm(int id) throws Exception;

    Film rejectFilm(int id, String comment) throws Exception;
}
