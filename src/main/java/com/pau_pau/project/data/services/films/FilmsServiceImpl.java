package com.pau_pau.project.data.services.films;

import com.pau_pau.project.data.repository.films.FilmsRepository;
import com.pau_pau.project.data.services.accounts.AccountService;
import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.films.FilmDTO;
import com.pau_pau.project.models.states.concretes.ModifiedFilmState;
import com.pau_pau.project.models.states.concretes.NewlyFilmState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmsServiceImpl implements FilmsService {

    @Autowired
    private FilmsRepository filmsRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public List<Film> findFilms(String title,
                                Date year,
                                String country,
                                String genre,
                                Date releaseDate,
                                Float budget) {
        return filmsRepository
                .findFilms(title, Timestamp.from(year.toInstant()), country, genre, Timestamp.from(releaseDate.toInstant()), budget)
                .stream()
                .filter(Film::isApproved)
                .collect(Collectors.toList());
    }

    @Override
    public List<Film> findActiveRequests(String title, Date year, String country, String genre, Date releaseDate, Float budget) {
        return filmsRepository
                .findFilms(title, Timestamp.from(year.toInstant()), country, genre, Timestamp.from(releaseDate.toInstant()), budget)
                .stream()
                .filter(film -> !film.isApproved())
                .collect(Collectors.toList());
    }

    @Override
    public Film findFilmById(int id) throws InstanceNotFoundException {
        if (!filmsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        return filmsRepository.findById(id).orElseThrow();
    }

    @Override
    public Film addFilm(FilmDTO filmDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Film film = Film.fromFilmDTOModel(filmDTO);
        film.setState(new NewlyFilmState(accountService.findByUsername(username)));
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
        film.setState(new ModifiedFilmState(film.getState()));
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

    @Override
    public Film publishFilm(int id) throws Exception {
        Account account = accountService.getAccount();

        Film film = filmsRepository.findById(id).get();
        film.getState().publish(account);
        filmsRepository.save(film);

        return film;
    }

    @Override
    public Film rejectFilm(int id, String comment) throws Exception {
        Account account = accountService.getAccount();

        Film film = filmsRepository.findById(id).get();
        film.getState().reject(account, comment);
        filmsRepository.save(film);

        return film;
    }
}
