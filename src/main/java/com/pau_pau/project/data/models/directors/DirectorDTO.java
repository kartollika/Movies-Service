package com.pau_pau.project.data.models.directors;

import io.swagger.annotations.ApiModelProperty;

public class DirectorDTO {

    public static DirectorDTO fromDirectorModel(Director director) {
        DirectorDTO directorDto = new DirectorDTO();
        directorDto.id = director.getId();
        directorDto.name = director.getName();
        directorDto.country = director.getCountry();
        return directorDto;
    }

    @ApiModelProperty(readOnly = true)
    private int id;

    private String name;

    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
