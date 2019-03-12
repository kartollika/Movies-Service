package com.pau_pau.project.data.services;

import com.pau_pau.project.data.models.FilmDTO;

import java.sql.Timestamp;

public interface FilmsService {

    Iterable<FilmDTO> findFilms(String title,
                                Timestamp year,
                                String country,
                                String genre,
                                Timestamp releaseDate,
                                Float budget);

    FilmDTO findFilmById(int id);

    void addFilm(FilmDTO film);

    void updateFilm(int id, FilmDTO film);

    void deleteFilmById(int id);
}
