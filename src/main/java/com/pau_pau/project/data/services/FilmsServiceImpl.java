package com.pau_pau.project.data.services;

import com.pau_pau.project.data.models.Film;
import com.pau_pau.project.data.models.FilmDTO;
import com.pau_pau.project.data.repository.films.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    private FilmsRepository filmsRepository;

    @Override
    public List<Film> findFilms(String title,
                                Date year,
                                String country,
                                String genre,
                                Date releaseDate,
                                Float budget) {
        return filmsRepository.findFilms(title, Timestamp.from(year.toInstant()), country, genre, Timestamp.from(releaseDate.toInstant()), budget);
    }

    @Override
    public Film findFilmById(int id) throws InstanceNotFoundException {
        if (!filmsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        return filmsRepository.findById(id).orElseThrow();
    }

    @Override
    public Film addFilm(FilmDTO filmDTO) {
        Film film = Film.fromFilmDTOModel(filmDTO);
        filmsRepository.save(film);
        return film;
    }

    @Override
    public Film updateFilm(int id, FilmDTO filmDTO) throws InstanceNotFoundException {
        if (!filmsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }

        filmDTO.setId(id);
        Film film = Film.fromFilmDTOModel(filmDTO);
        filmsRepository.save(film);
        return film;
    }

    @Override
    public Film deleteFilmById(int id) throws InstanceNotFoundException {
        if (!filmsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }

        Film film = filmsRepository.findById(id).get();
        filmsRepository.deleteById(id);
        return film;
    }
}
