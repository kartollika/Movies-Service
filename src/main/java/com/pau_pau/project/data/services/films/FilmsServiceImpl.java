package com.pau_pau.project.data.services.films;

import com.pau_pau.project.data.repository.films.FilmsRepository;
import com.pau_pau.project.data.services.accounts.AccountService;
import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.films.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InstanceNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
        return filmsRepository.findFilms(title, Timestamp.from(year.toInstant()), country, genre, Timestamp.from(releaseDate.toInstant()));
    }

    @Override
    public Film findFilmById(int id) throws InstanceNotFoundException{
        if (!filmsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        Film film = filmsRepository.findById(id).orElseThrow();
        if (film != null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
           try {
                Account account = accountService.findByUsername(username);
                accountService.addToHistory(username, id);
                account.addFilmToHistory(film);
            } catch (Exception e){
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        return film;
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
