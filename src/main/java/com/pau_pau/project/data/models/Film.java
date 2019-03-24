package com.pau_pau.project.data.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "films")
public class Film {

    public static Film fromFilmDTOModel(FilmDTO filmDTO) {
        Film film = new Film();
        film.id = filmDTO.getId();
        film.title = filmDTO.getTitle();
        film.genre = filmDTO.getGenre();
        film.year = filmDTO.getYear();
        film.country = filmDTO.getCountry();
        for (DirectorDTO director : filmDTO.getDirectors()) {
            film.directors.add(Director.fromDirectorDTOModel(director));
        }
        film.budget = film.getBudget();
        film.release = film.getRelease();
        return film;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String title;

    @Column
    @CreationTimestamp
    private Timestamp year;

    @Column
    private String country;

    @Column
    @ManyToMany
    @JoinTable(
            name = "films_directors",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors = new HashSet<>();

    @Column
    private String genre;

    @Column(name = "release")
    @CreationTimestamp
    private Timestamp release;

    @Column
    private float budget;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getYear() {
        return year;
    }

    public void setYear(Timestamp year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Timestamp getRelease() {
        return release;
    }

    public void setRelease(Timestamp release) {
        this.release = release;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        Film otherObj = (Film) other;

        //TODO are you sure that this is right (above)? If you can improve, do it
        boolean res = true;
        res = res && this.country.equals(otherObj.getCountry());
        double eps = 10e-10;
        res = res && (Math.abs(this.budget - otherObj.budget) <= eps);
        res = res && (this.genre.equals(otherObj.genre));
        res = res && (this.title.equals(otherObj.title));
        //TODO need to fix different hours
//        res = res && (this.release.equals(otherObj.release));
//        System.out.println(this.release + " \n" + otherObj.release);
//        System.out.println("res4 = " + res);
//        res = res && (this.year.equals(otherObj.year));
//        System.out.println(this.year + "\n" + otherObj.year);
//        System.out.println("res6 = " + res);
//        res = res && (this.directors.equals(otherObj.directors));
//        System.out.println("res7 = " + res);
        return res;
    }

    //TODO need improvement?
    @Override
    public int hashCode()
    {
        return 76+133*id;
    }
}
