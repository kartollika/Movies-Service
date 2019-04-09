package com.pau_pau.project.data.services.directors;

import com.pau_pau.project.models.directors.DirectorDTO;

import javax.management.InstanceNotFoundException;

public interface DirectorsService {
    Iterable<DirectorDTO> findDirectors(String name,
                                        String country);

    DirectorDTO findDirectorById(int id) throws InstanceNotFoundException;

    DirectorDTO addDirector(DirectorDTO director);

    DirectorDTO updateDirector(int id, DirectorDTO director) throws InstanceNotFoundException;

    DirectorDTO deleteDirectorById(int id) throws InstanceNotFoundException;
}
