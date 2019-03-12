package com.pau_pau.project.data.services;

import com.pau_pau.project.data.models.DirectorDTO;

public interface DirectorsService {
    Iterable<DirectorDTO> findDirectors(String name,
                                        String country);

    DirectorDTO findDirectorById(int id);

    void addDirector(DirectorDTO director);

    void updateDirector(int id, DirectorDTO director);

    void deleteDirectorById(int id);
}
