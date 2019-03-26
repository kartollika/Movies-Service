package com.pau_pau.project.data.services;

import com.pau_pau.project.data.models.Film;
import com.pau_pau.project.data.models.FilmDTO;
import com.pau_pau.project.data.repository.films.FilmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public FilmDTO findFilmById(int id) throws InstanceNotFoundException {
        if (!filmsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        return FilmDTO.fromFilmModel(filmsRepository.findById(id).orElse(null));
    }

    @Override
    public FilmDTO addFilm(FilmDTO filmDTO) {
        Film film = Film.fromFilmDTOModel(filmDTO);
        filmsRepository.save(film);
        return FilmDTO.fromFilmModel(film);
    }

    @Override
    public FilmDTO updateFilm(int id, FilmDTO filmDTO) throws InstanceNotFoundException {
        if (!filmsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }

        Film film = Film.fromFilmDTOModel(filmDTO);
        filmDTO.setId(id);
        filmsRepository.deleteById(id);
        filmsRepository.save(Film.fromFilmDTOModel(filmDTO));
        return FilmDTO.fromFilmModel(film);
    }

    @Override
    public FilmDTO deleteFilmById(int id) throws InstanceNotFoundException {
        if (!filmsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }

        FilmDTO film = FilmDTO.fromFilmModel(filmsRepository.findById(id).get());
        filmsRepository.deleteById(id);
        return film;
    }
}
