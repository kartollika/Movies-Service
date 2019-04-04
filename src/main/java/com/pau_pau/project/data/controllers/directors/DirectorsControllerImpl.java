package com.pau_pau.project.data.controllers.directors;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.models.DirectorDTO;
import com.pau_pau.project.data.services.DirectorsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InstanceNotFoundException;

@RestController
@CrossOrigin
@Api(tags = "Directors", value = "Directors", description = "Api for operations with directors")
@RequestMapping(ControllerConstants.DIRECTORS_URL)
public class DirectorsControllerImpl implements DirectorsController {

    private static final String DEFAULT_DIRECTOR_NAME = "";
    private static final String DEFAULT_DIRECTOR_COUNTRY = "";


    @Autowired
    private DirectorsServiceImpl directorsService;

    /* ================================
                 GET METHODS
     ================================== */

    @Override
    @ApiOperation(value = "Get list of Directors", response = DirectorDTO.class, responseContainer = "List")
    @GetMapping
    public Iterable<DirectorDTO> getDirectors(@RequestParam(defaultValue = DEFAULT_DIRECTOR_NAME) String name,
                                              @RequestParam(defaultValue = DEFAULT_DIRECTOR_COUNTRY) String country) {
        return directorsService.findDirectors(name, country);
    }

    @Override
    @ApiOperation(value = "Get Director by id", response = DirectorDTO.class)
    @GetMapping(ControllerConstants.DIRECTOR_BY_ID)
    public DirectorDTO getDirectorById(@RequestParam int directorId) {
        try {
            return directorsService.findDirectorById(directorId);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================
                 POST METHODS
     ================================== */

    @Override
    @ApiOperation(value = "Add new director", response = DirectorDTO.class)
    @PostMapping
    public DirectorDTO addDirector(@RequestBody DirectorDTO director) {
        return directorsService.addDirector(director);
    }

    /* ================================
                 PUT METHODS
     ================================== */

    @Override
    @ApiOperation(value = "Update existing director", response = DirectorDTO.class)
    @PutMapping
    public DirectorDTO updateDirector(@RequestParam int directorId, @RequestBody DirectorDTO director) {
        try {
            return directorsService.updateDirector(directorId, director);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================
                 DELETE METHODS
     ================================== */

    @Override
    @ApiOperation(value = "Delete director", response = DirectorDTO.class)
    @DeleteMapping
    public DirectorDTO deleteDirector(@RequestParam int directorId) {
        try {
            return directorsService.deleteDirectorById(directorId);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
}
