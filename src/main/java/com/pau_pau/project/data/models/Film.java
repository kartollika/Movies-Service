package com.pau_pau.project.data.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "films")
public class Film {

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
    private Set<Film> directors = new HashSet<>();

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
        return (Timestamp) year.clone();
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

    public Set<Film> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Film> directors) {
        this.directors = directors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Timestamp getRelease() {
        return (Timestamp) release.clone();
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
}
