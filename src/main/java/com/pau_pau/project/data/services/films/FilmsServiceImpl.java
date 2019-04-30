package com.pau_pau.project.data.services.films;

import com.pau_pau.project.data.repository.films.FilmsRepository;
import com.pau_pau.project.data.services.accounts.AccountService;
import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.films.FilmDTO;
import com.pau_pau.project.models.states.concretes.ModifiedFilmState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.sql.Timestamp;
import java.util.*;
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
                                Date releaseDate) {
        List<Film> films = filmsRepository
                .findFilms(title,
                        Timestamp.from(year.toInstant()),
                        country, genre,
                        Timestamp.from(releaseDate.toInstant()))
                .stream()
                .filter(Film::isApproved)
                .collect(Collectors.toList());
        return getLatestFilmsFirst(films);
    }

    @Override
    public List<Film> findActiveRequests(String title,
                                         Date year,
                                         String country,
                                         String genre,
                                         Date releaseDate) {
        return filmsRepository
                .findFilms(title, Timestamp.from(year.toInstant()), country, genre, Timestamp.from(releaseDate.toInstant()))
                .stream()
                .filter(film -> !film.isApproved())
                .collect(Collectors.toList());
    }

    private List<Film> getLatestFilmsFirst(List<Film> films) {
        Calendar calendar = Calendar.getInstance();
        GregorianCalendar weekEarly = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        GregorianCalendar weekOlder = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        weekEarly.roll(Calendar.WEEK_OF_YEAR, -1);
        weekOlder.roll(Calendar.WEEK_OF_YEAR, 1);

        List<Film> newsOrderedFilms = new ArrayList<>();
        ListIterator<Film> filmIterator = films.listIterator();
        while (filmIterator.hasNext()) {
            Film currentFilm = filmIterator.next();
            if (currentFilm.getCreationDate().after(weekEarly.getTime())
                    && currentFilm.getCreationDate().before(weekOlder.getTime())) {
                newsOrderedFilms.add(currentFilm);
                filmIterator.remove();
            }
        }
        newsOrderedFilms.addAll(films);
        return newsOrderedFilms;
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
        Film film = Film.fromFilmDTOModel(filmDTO);
        initFilmState(film);
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
    public Film getRandomFilm() {
        return filmsRepository.getRandomFilm();
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

    private Film initFilmState(Film film) throws Exception {
        Account account = accountService.getAccount();
        film.initFilmState(account);
        return film;
    }
}
