package com.pau_pau.project;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class Controller {
    @GetMapping(value = "/film")
    public List<Movies> getFilms() {
        ArrayList<Movies> movies = new ArrayList<>();
        Movies movie1 = new Movies(1,"film1", "director1", 2019);
        Movies movie2 = new Movies(2,"film2", "director1", 2017);
        Movies movie3 = new Movies(3,"film3", "director2", 2011);
        Movies movie4 = new Movies(4,"film4", "director2", 2011);
        Movies movie5 = new Movies(5,"film4", "director2", 2011);
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);
        movies.add(movie5);
        return movies;
    }
}
