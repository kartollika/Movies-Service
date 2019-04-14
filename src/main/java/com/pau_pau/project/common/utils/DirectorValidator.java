package com.pau_pau.project.common.utils;

import com.pau_pau.project.models.directors.DirectorDTO;
import org.springframework.stereotype.Component;

@Component
public class DirectorValidator {

    public void validate(String name, String country) {
        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setName(name);
        directorDTO.setCountry(country);

        validate(directorDTO);
    }

    public void validate(DirectorDTO director) {
        if (director.getName().length() > 60) {
            throw new IllegalArgumentException("Parameter name has more than 60 symbols");
        }
    }
}
