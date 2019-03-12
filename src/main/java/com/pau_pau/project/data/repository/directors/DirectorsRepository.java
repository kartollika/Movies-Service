package com.pau_pau.project.data.repository.directors;

import com.pau_pau.project.data.models.Director;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorsRepository extends CrudRepository<Director, Integer> {

    @Query(value = "select d from Director d where " +
            "upper(d.name) like concat('%', upper(:name), '%') and " +
            "upper(d.country) like concat('%', upper(:country), '%')")
    public List<Director> findDirectors(@Param("name") String name,
                                        @Param("country") String country);
}
