package com.pau_pau.project.data.services;

import com.pau_pau.project.data.models.Director;
import com.pau_pau.project.data.models.DirectorDTO;
import com.pau_pau.project.data.repository.directors.DirectorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.List;

@Service
public class DirectorsServiceImpl implements DirectorsService {

    @Autowired
    private DirectorsRepository directorsRepository;

    @Override
    public List<Director> findDirectors(String name, String country) {
        return directorsRepository.findDirectors(name, country);
    }

    @Override
    public Director findDirectorById(int id) throws InstanceNotFoundException {
        if (!directorsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        return directorsRepository.findById(id).orElse(null);
    }

    @Override
    public Director addDirector(DirectorDTO director) {
        Director directorToDB = Director.fromDirectorDTOModel(director);
        directorsRepository.save(directorToDB);
        return directorToDB;
    }

    @Override
    public Director updateDirector(int id, DirectorDTO directorDTO) throws InstanceNotFoundException {
        if (!directorsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        directorDTO.setId(id);
        Director director = Director.fromDirectorDTOModel(directorDTO);
        directorsRepository.save(director);
        return director;
    }

    @Override
    public Director deleteDirectorById(int id) throws InstanceNotFoundException {
        if (!directorsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        Director director = directorsRepository.findById(id).get();
        directorsRepository.deleteById(id);
        return director;
    }
}
