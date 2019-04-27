package com.pau_pau.project.models.states;

public class FilmStateDTO {

    public static FilmStateDTO fromFilmModel(FilmState state) {
        FilmStateDTO filmStateDTO = new FilmStateDTO();
        filmStateDTO.ownerId = state.ownerId;
        if (state instanceof Commentable) {
            filmStateDTO.comment = ((Commentable) state).comment;
        }
        return filmStateDTO;
    }

    private String comment;
    private int ownerId;

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
}
