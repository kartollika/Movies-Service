package com.pau_pau.project.data.controllers.directors;

import com.pau_pau.project.data.models.DirectorDTO;

public interface DirectorsController {

    Iterable<DirectorDTO> getDirectors(String name, String country);

    DirectorDTO getDirectorById(int directorId);

    DirectorDTO addDirector(DirectorDTO director);

    DirectorDTO updateDirector(int directorId, DirectorDTO director);

    DirectorDTO deleteDirector(int directorId);
}
