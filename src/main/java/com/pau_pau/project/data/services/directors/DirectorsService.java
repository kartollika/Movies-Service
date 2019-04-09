package com.pau_pau.project.data.services.directors;

import com.pau_pau.project.models.directors.Director;
import com.pau_pau.project.models.directors.DirectorDTO;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface DirectorsService {
    List<Director> findDirectors(String name,
                                 String country);

    Director findDirectorById(int id) throws InstanceNotFoundException;

    Director addDirector(DirectorDTO director);

    Director updateDirector(int id, DirectorDTO director) throws InstanceNotFoundException;

    Director deleteDirectorById(int id) throws InstanceNotFoundException;
}
