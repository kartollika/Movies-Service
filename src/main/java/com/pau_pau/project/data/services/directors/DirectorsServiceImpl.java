package com.pau_pau.project.data.services.directors;

import com.pau_pau.project.data.repository.directors.DirectorsRepository;
import com.pau_pau.project.models.directors.Director;
import com.pau_pau.project.models.directors.DirectorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectorsServiceImpl implements DirectorsService {

    @Autowired
    private DirectorsRepository directorsRepository;

    @Override
    public Iterable<DirectorDTO> findDirectors(String name, String country) {
        Iterable<Director> directorsDatabase = directorsRepository.findDirectors(name, country);
        List<DirectorDTO> directorDTOList = new ArrayList<>();
        for (Director director : directorsDatabase) {
            directorDTOList.add(DirectorDTO.fromDirectorModel(director));
        }
        return directorDTOList;
    }

    @Override
    public DirectorDTO findDirectorById(int id) throws InstanceNotFoundException {
        if (!directorsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        return DirectorDTO.fromDirectorModel(directorsRepository.findById(id).orElseThrow());
    }

    @Override
    public DirectorDTO addDirector(DirectorDTO director) {
        Director directorToDB = Director.fromDirectorDTOModel(director);
        directorsRepository.save(directorToDB);
        return DirectorDTO.fromDirectorModel(directorToDB);
    }

    @Override
    public DirectorDTO updateDirector(int id, DirectorDTO director) throws InstanceNotFoundException {
        if (!directorsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        director.setId(id);
        directorsRepository.save(Director.fromDirectorDTOModel(director));
        return director;
    }

    @Override
    public DirectorDTO deleteDirectorById(int id) throws InstanceNotFoundException {
        if (!directorsRepository.existsById(id)) {
            throw new InstanceNotFoundException();
        }
        DirectorDTO director = DirectorDTO.fromDirectorModel(directorsRepository.findById(id).get());
        directorsRepository.deleteById(id);
        return director;
    }
}
