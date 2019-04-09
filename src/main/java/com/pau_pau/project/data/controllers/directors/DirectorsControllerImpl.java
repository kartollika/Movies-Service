package com.pau_pau.project.data.controllers.directors;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.models.DirectorDTO;
import com.pau_pau.project.data.services.DirectorsServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Directors", value = "Directors", description = "Api for operations with directors")
@RequestMapping(ControllerConstants.DIRECTORS_URL)
public class DirectorsControllerImpl implements DirectorsController {


    @Autowired
    private DirectorsServiceImpl directorsService;

    /* ================================
                 GET METHODS
     ================================== */

    @Override
    public List<DirectorDTO> getDirectors(String name, String country) {
        return directorsService.findDirectors(name, country)
                .stream().map(DirectorDTO::fromDirectorModel).collect(Collectors.toList());
    }

    @Override
    public DirectorDTO getDirectorById(int directorId) {
        try {
            return DirectorDTO.fromDirectorModel(directorsService.findDirectorById(directorId));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================
                 POST METHODS
     ================================== */

    @Override
    public DirectorDTO addDirector(DirectorDTO director) {
        return DirectorDTO.fromDirectorModel(directorsService.addDirector(director));
    }

    /* ================================
                 PUT METHODS
     ================================== */

    @Override
    public DirectorDTO updateDirector(int directorId, DirectorDTO director) {
        try {
            return DirectorDTO.fromDirectorModel(directorsService.updateDirector(directorId, director));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================
                 DELETE METHODS
     ================================== */

    @Override

    public DirectorDTO deleteDirector(int directorId) {
        try {
            return DirectorDTO.fromDirectorModel(directorsService.deleteDirectorById(directorId));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
}
