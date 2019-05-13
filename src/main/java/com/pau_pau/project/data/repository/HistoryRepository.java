package com.pau_pau.project.data.repository;

import com.pau_pau.project.models.history.History;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends CrudRepository<History, Integer> {
}
