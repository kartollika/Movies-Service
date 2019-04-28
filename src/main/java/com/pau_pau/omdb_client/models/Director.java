package com.pau_pau.omdb_client.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "directors")
public class Director {
    public Director() {
    }

    public Director(String name, String country) {
        this.name = name;
        this.country = country;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private String country;

    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "directors")
    private Set<FilmToMoviesService> films;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
