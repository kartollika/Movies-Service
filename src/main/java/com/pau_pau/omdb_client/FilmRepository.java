package com.pau_pau.omdb_client;

import com.pau_pau.omdb_client.models.Director;
import com.pau_pau.omdb_client.models.FilmToMoviesService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends JpaRepository<FilmToMoviesService, Integer> {

}
