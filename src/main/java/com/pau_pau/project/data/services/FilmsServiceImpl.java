package com.pau_pau.project.data.services;

import com.pau_pau.project.data.models.Film;
import com.pau_pau.project.data.models.FilmDTO;
import com.pau_pau.project.data.repository.films.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    private FilmsRepository filmsRepository;

    @Override
    public Iterable<FilmDTO> findFilms(String title,
                                       Timestamp year,
                                       String country,
                                       String genre,
                                       Timestamp releaseDate,
                                       Float budget) {
        List<FilmDTO> filmDTOList = new ArrayList<>();
        Iterable<Film> filmsDataBase = filmsRepository.findFilms(title, year, country, genre, releaseDate, budget);
        filmsDataBase.forEach(film -> filmDTOList.add(FilmDTO.fromFilmModel(film)));
        return filmDTOList;
    }

    @Override
    public FilmDTO findFilmById(int id) {
        return FilmDTO.fromFilmModel(filmsRepository.findById(id).orElseThrow());
    }

    @Override
    public void addFilm(FilmDTO filmDTO) {
        filmsRepository.save(Film.fromFilmDTOModel(filmDTO));
    }

    @Override
    public void updateFilm(int id, FilmDTO filmDTO) {
        filmDTO.setId(id);
        filmsRepository.save(Film.fromFilmDTOModel(filmDTO));
    }

    @Override
    public void deleteFilmById(int id) {
        filmsRepository.deleteById(id);
    }
}
