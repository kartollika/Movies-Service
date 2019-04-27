package com.pau_pau.omdb_client;

import com.pau_pau.omdb_client.http.OmdbClient;
import com.pau_pau.omdb_client.models.Director;
import com.pau_pau.omdb_client.models.FilmFromOmdb;
import com.pau_pau.omdb_client.models.FilmToMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransferService {
    @Autowired
    private OmdbClient client;
    @Autowired
    private FilmRepository repo;


    public void run() throws ParseException, InterruptedException {
        List<String> ids = client.getIds();
        for (String id : ids) {
            try {

                FilmFromOmdb film = client.getFilmOmdb(id);
                if (
                        !film.getYear().equals("N/A")
                                && !film.getActors().equals("N/A")
                                && !film.getCountry().equals("N/A")
                                && !film.getDirector().equals("N/A")
                                && !film.getGenre().equals("N/A")
                                && !film.getPlot().equals("N/A")
                                && !film.getPoster().equals("N/A")
                                && !film.getTitle().equals("N/A")
                ) {
                    System.out.println(film.getTitle());
                    repo.save(prepare(film));


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private FilmToMoviesService prepare(FilmFromOmdb film) throws ParseException {
        FilmToMoviesService movie = new FilmToMoviesService();
        movie.setTitle(film.getTitle());
        // иначе никак :)
        DateFormat format = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        movie.setYear(format.parse(film.getYear()));

        movie.setCountry(film.getCountry());

        DateFormat format2 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        movie.setGenre(film.getGenre());
        movie.setRelease(format2.parse(film.getReleased()));
        movie.setPoster(film.getPoster());
        movie.setActors(film.getActors());
        movie.setDescription(film.getPlot());
        movie.getDirectors().add(new Director(film.getDirector(), film.getCountry()));
        return movie;
    }
}
