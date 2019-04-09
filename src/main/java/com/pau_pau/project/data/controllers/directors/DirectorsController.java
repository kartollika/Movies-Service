package com.pau_pau.project.data.controllers.directors;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.directors.DirectorDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface DirectorsController {

    String DEFAULT_DIRECTOR_NAME = "";
    String DEFAULT_DIRECTOR_COUNTRY = "";

    @ApiOperation(value = "Get list of Directors", response = DirectorDTO.class, responseContainer = "List")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<DirectorDTO> getDirectors(@RequestParam(defaultValue = DEFAULT_DIRECTOR_NAME) String name,
                                   @RequestParam(defaultValue = DEFAULT_DIRECTOR_COUNTRY) String country);

    @ApiOperation(value = "Get Director by id", response = DirectorDTO.class)
    @GetMapping(ControllerConstants.DIRECTOR_URL_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    DirectorDTO getDirectorById(@PathVariable(name = ControllerConstants.DIRECTOR_PATH_ID) int directorId);

    @ApiOperation(value = "Add new director", response = DirectorDTO.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DirectorDTO addDirector(@RequestBody DirectorDTO director);

    @ApiOperation(value = "Update existing director", response = DirectorDTO.class)
    @PutMapping(ControllerConstants.DIRECTOR_URL_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    DirectorDTO updateDirector(@PathVariable(name = ControllerConstants.DIRECTOR_PATH_ID) int directorId, @RequestBody DirectorDTO director);

    @ApiOperation(value = "Delete director", response = DirectorDTO.class)
    @DeleteMapping(ControllerConstants.DIRECTOR_URL_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    DirectorDTO deleteDirector(@PathVariable(name = ControllerConstants.DIRECTOR_PATH_ID) int directorId);
}
