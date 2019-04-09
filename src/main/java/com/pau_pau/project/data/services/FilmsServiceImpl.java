package com.pau_pau.project.data.services;

import com.pau_pau.project.data.models.Film;
import com.pau_pau.project.data.models.FilmDTO;
import com.pau_pau.project.data.models.api.EntityNotFoundException;
import com.pau_pau.project.data.repository.films.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    private FilmsRepository filmsRepository;

    @Override
    public List<FilmDTO> findFilms(String title,
                                   Date year,
                                   String country,
                                   String genre,
                                   Date releaseDate,
                                   Float budget) {
        List<FilmDTO> filmDTOList = new ArrayList<>();
        Iterable<Film> filmsDataBase = filmsRepository.findFilms(title, Timestamp.from(year.toInstant()), country, genre, Timestamp.from(releaseDate.toInstant()), budget);
        filmsDataBase.forEach(film -> filmDTOList.add(FilmDTO.fromFilmModel(film)));
        return filmDTOList;
    }

    @Override
    public FilmDTO findFilmById(int id) throws EntityNotFoundException {
        if (!filmsRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        return FilmDTO.fromFilmModel(filmsRepository.findById(id).orElseThrow());
    }

    @Override
    public FilmDTO addFilm(FilmDTO filmDTO) {
        Film film = Film.fromFilmDTOModel(filmDTO);
        filmsRepository.save(film);
        return FilmDTO.fromFilmModel(film);
    }

    @Override
    public FilmDTO updateFilm(int id, FilmDTO filmDTO) throws EntityNotFoundException {
        if (!filmsRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        Film film = Film.fromFilmDTOModel(filmDTO);
        filmDTO.setId(id);
        filmsRepository.save(Film.fromFilmDTOModel(filmDTO));
        return FilmDTO.fromFilmModel(film);
    }

    @Override
    public FilmDTO deleteFilmById(int id) throws NoSuchElementException {
        FilmDTO film = FilmDTO.fromFilmModel(filmsRepository.findById(id).get());
        filmsRepository.deleteById(id);
        return film;
    }
}
