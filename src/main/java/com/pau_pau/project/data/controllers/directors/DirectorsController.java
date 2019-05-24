package com.pau_pau.project.data.controllers.directors;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.directors.DirectorDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pau_pau.project.data.controllers.ControllerConstants.*;

public interface DirectorsController {

    String DEFAULT_DIRECTOR_NAME = "";
    String DEFAULT_DIRECTOR_COUNTRY = "";

    @ApiOperation(value = "Get list of Directors. " + AVAILABLE_EVERYONE, response = DirectorDTO.class, responseContainer = "List")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<DirectorDTO> getDirectors(@RequestParam(defaultValue = DEFAULT_DIRECTOR_NAME) String name,
                                   @RequestParam(defaultValue = DEFAULT_DIRECTOR_COUNTRY) String country);

    @ApiOperation(value = "Get Director by id. " + AVAILABLE_EVERYONE, response = DirectorDTO.class)
    @GetMapping(DIRECTOR_URL_BY_ID)
    @ResponseStatus(HttpStatus.OK)
    DirectorDTO getDirectorById(@PathVariable(name = ControllerConstants.DIRECTOR_PATH_ID) int directorId);

    @ApiOperation(value = "Add new director. " + AVAILABLE_ADMIN, response = DirectorDTO.class, authorizations = @Authorization(value = "Bearer"))
    @PostMapping
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.CREATED)
    DirectorDTO addDirector(@RequestBody DirectorDTO director);

    @ApiOperation(value = "Update existing director. " + AVAILABLE_ADMIN, response = DirectorDTO.class, authorizations = @Authorization(value = "Bearer"))
    @PutMapping(DIRECTOR_URL_BY_ID)
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    DirectorDTO updateDirector(@PathVariable(name = ControllerConstants.DIRECTOR_PATH_ID) int directorId, @RequestBody DirectorDTO director);

    @ApiOperation(value = "Delete director. " + AVAILABLE_ADMIN, response = DirectorDTO.class, authorizations = @Authorization(value = "Bearer"))
    @DeleteMapping(DIRECTOR_URL_BY_ID)
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    DirectorDTO deleteDirector(@PathVariable(name = ControllerConstants.DIRECTOR_PATH_ID) int directorId);
}
