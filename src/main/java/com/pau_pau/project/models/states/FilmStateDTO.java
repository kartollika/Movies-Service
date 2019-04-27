package com.pau_pau.project.models.states;

public class FilmStateDTO {

    public static FilmStateDTO fromFilmModel(FilmState state) {
        FilmStateDTO filmStateDTO = new FilmStateDTO();
        filmStateDTO.ownerId = state.ownerId;
        if (state instanceof Commentable) {
            filmStateDTO.comment = ((Commentable) state).comment;
        }
        filmStateDTO.status = state.getStatusName();
        return filmStateDTO;
    }

    private String comment;
    private int ownerId;
    private FilmStatus status;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public FilmStatus getStatus() {
        return status;
    }

    public void setStatus(FilmStatus status) {
        this.status = status;
    }
}
