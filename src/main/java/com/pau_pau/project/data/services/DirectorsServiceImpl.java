package com.pau_pau.project.data.services;

import com.pau_pau.project.data.models.Director;
import com.pau_pau.project.data.models.DirectorDTO;
import com.pau_pau.project.data.repository.directors.DirectorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public DirectorDTO findDirectorById(int id) {
        return DirectorDTO.fromDirectorModel(directorsRepository.findById(id).orElseThrow());
    }

    @Override
    public void addDirector(DirectorDTO director) {
        directorsRepository.save(Director.fromDirectorDTOModel(director));
    }

    @Override
    public void updateDirector(int id, DirectorDTO director) {
        director.setId(id);
        directorsRepository.save(Director.fromDirectorDTOModel(director));
    }

    @Override
    public void deleteDirectorById(int id) {
        directorsRepository.deleteById(id);
    }
}
