package com.pau_pau.project.data.repository.films;

import com.pau_pau.project.data.models.Film;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface FilmsRepository extends CrudRepository<Film, Integer> {


    @Query(value = "SELECT f FROM Film f WHERE " +
            "upper(f.title) like concat('%', upper(:title), '%') and " +
            "Upper(f.country) like concat('%', upper(:country), '%') and " +
            "f.year >= :year and " +
            "f.release >= :release and " +
            "upper(f.genre) like concat('%', upper(:genre), '%') " +
            "and f.budget >= :budget")
    Iterable<Film> findFilms(@Param("title") String title,
                             @Param("year") Date year,
                             @Param("country") String country,
                             @Param("genre") String genre,
                             @Param("release") Date release,
                             @Param("budget") float budget);
}