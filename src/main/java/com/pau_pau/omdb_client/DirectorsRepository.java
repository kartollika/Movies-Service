package com.pau_pau.omdb_client;

import com.pau_pau.omdb_client.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorsRepository extends JpaRepository<Director, Integer> {
    Director getDirectorByName(String name);
}

