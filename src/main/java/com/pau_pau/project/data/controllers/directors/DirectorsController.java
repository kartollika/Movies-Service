package com.pau_pau.project.data.controllers.directors;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.models.Director;
import com.pau_pau.project.data.repository.directors.DirectorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.DIRECTORS_URL)
public class DirectorsController {

    @Autowired
    private DirectorsRepository directorsRepository;

    /* ================================
                 GET METHODS
     ================================== */

    @GetMapping
    public List<Director> getDirectors(@RequestParam String name,
                                       @RequestParam String country) {
        return directorsRepository.findDirectors(name, country);
    }

    @GetMapping(ControllerConstants.DIRECTOR_BY_ID)
    public Director getDirectorById(@RequestParam int directorId) {
        return directorsRepository.findById(directorId).orElseThrow();
    }

    /* ================================
                 POST METHODS
     ================================== */

    @PostMapping
    public void addDirector(@ModelAttribute Director director) {
        directorsRepository.save(director);
    }

    /* ================================
                 PUT METHODS
     ================================== */

    @PutMapping
    public void updateDirector(@RequestParam int directorId, @ModelAttribute Director director) {
        director.setId(directorId);
        directorsRepository.save(director);
    }

    /* ================================
                 DELETE METHODS
     ================================== */

    @DeleteMapping
    public void deleteDirector(@RequestParam int directorId) {
        directorsRepository.deleteById(directorId);
    }
}
