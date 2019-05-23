package com.pau_pau.project.data.services.films;

import com.pau_pau.project.data.repository.accounts.AccountsRepository;
import com.pau_pau.project.data.repository.films.FilmsRepository;
import com.pau_pau.project.data.services.accounts.AccountService;
import com.pau_pau.project.data.services.directors.DirectorsService;
import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.films.FilmDTO;
import com.pau_pau.project.models.states.FilmStatus;
import com.pau_pau.project.models.states.concretes.ApprovedFilmState;
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

    @Autowired
    private DirectorsService directorsService;

    @Autowired
    private AccountsRepository accountsRepository;

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
    public List<Film> findActiveRequests() {
        return filmsRepository
                .findAll()
                .stream()
                .filter(film -> !film.isApproved())
                .collect(Collectors.toList());
    }

    @Override
    public List<Film> findActiveRequestsForAccount(int accountId) {
        return findActiveRequests()
                .stream()
                .filter(film -> film.getState().getOwnerId() == accountId)
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
    public Film findFilmById(int id) throws InstanceNotFoundException{
        if (!filmsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }

        Film film = filmsRepository.findById(id).get();
        if (film.getState().getStatusName().equals(FilmStatus.APPROVED)) {
            return film;
        } else {
            return null;
        }
    }

    @Override
    public Film addFilm(FilmDTO filmDTO) throws Exception {
        Film film = Film.fromFilmDTOModel(filmDTO);
        for (Integer directorId : film.getDirectorsId()) {
            film.getDirectors().add(directorsService.findDirectorById(directorId));
        }
        initFilmState(film);
        filmsRepository.save(film);
        return film;
    }

    @Override
    public Film updateFilm(int id, FilmDTO filmDTO) throws Exception {
        if (!filmsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        filmDTO.setId(id);
        Film film = Film.fromFilmDTOModel(filmDTO);
        for (Integer directorId : film.getDirectorsId()) {
            film.getDirectors().add(directorsService.findDirectorById(directorId));
        }

        if (accountService.getAccount().getPermissionsLevel().equals(Role.EDITOR)) {
            film.setState(new ModifiedFilmState(accountService.getAccount()));
        } else if (accountService.getAccount().getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new ApprovedFilmState(accountService.getAccount()));
        }

        filmsRepository.deleteById(id);
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
        film.publish(account);
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

    private void autoPublishFilm(Account account, Film film) throws Exception {
        try {
            film.publish(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFilmState(Film film) throws Exception {
        Account account = accountService.getAccount();
        film.initFilmState(account);
    }
}
