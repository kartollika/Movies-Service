package com.pau_pau.project.models.films;

public class OrderedFilmDTO {
    private FilmDTO filmDTO;
    private int order;

    public OrderedFilmDTO(FilmDTO filmDTO, int order){
        this.filmDTO = filmDTO;
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public FilmDTO getFilmDTO() {
        return filmDTO;
    }

    public void setFilmDTO(FilmDTO filmDTO) {
        this.filmDTO = filmDTO;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (this.getClass() != other.getClass()) return false;
        OrderedFilmDTO otherObj = (OrderedFilmDTO) other;
        return otherObj.filmDTO.equals(this.filmDTO) && otherObj.order == this.order;
    }

    @Override
    public int hashCode()
    {
        return filmDTO.hashCode() * 72 + order * 13;
    }
}
