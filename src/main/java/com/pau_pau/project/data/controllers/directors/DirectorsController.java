package com.pau_pau.project.data.controllers.directors;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.models.DirectorDTO;
import com.pau_pau.project.data.services.DirectorsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.DIRECTORS_URL)
public class DirectorsController {

    @Autowired
    private DirectorsServiceImpl directorsService;

    /* ================================
                 GET METHODS
     ================================== */

    @GetMapping
    public Iterable<DirectorDTO> getDirectors(@RequestParam String name,
                                              @RequestParam String country) {
        return directorsService.findDirectors(name, country);
    }

    @GetMapping(ControllerConstants.DIRECTOR_BY_ID)
    public DirectorDTO getDirectorById(@RequestParam int directorId) {
        return directorsService.findDirectorById(directorId);
    }

    /* ================================
                 POST METHODS
     ================================== */

    @PostMapping
    public void addDirector(@ModelAttribute DirectorDTO director) {
        directorsService.addDirector(director);
    }

    /* ================================
                 PUT METHODS
     ================================== */

    @PutMapping
    public void updateDirector(@RequestParam int directorId, @ModelAttribute DirectorDTO director) {
        directorsService.updateDirector(directorId, director);
    }

    /* ================================
                 DELETE METHODS
     ================================== */

    @DeleteMapping
    public void deleteDirector(@RequestParam int directorId) {
        directorsService.deleteDirectorById(directorId);
    }
}
