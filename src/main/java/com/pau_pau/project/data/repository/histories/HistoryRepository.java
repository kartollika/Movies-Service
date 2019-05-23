package com.pau_pau.project.data.repository.histories;

import com.pau_pau.project.models.history.History;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Integer> {

    List<History> getByFilmId(int filmId);
}
