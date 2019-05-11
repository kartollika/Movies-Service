package com.pau_pau.project.models.directors;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pau_pau.project.models.films.Film;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "directors")
public class Director {

    public static Director fromDirectorDTOModel(DirectorDTO directorDTO) {
        Director director = new Director();
        director.id = directorDTO.getId();
        director.name = directorDTO.getName();
        director.country = directorDTO.getCountry();
        return director;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private String country;

    @PreRemove
    private void deleteDirectorsFromFilms() {
        for (Film f: films) {
            f.getDirectors().remove(this);
        }
    }

    @ManyToMany(mappedBy = "directors", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Film> films;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }
}
