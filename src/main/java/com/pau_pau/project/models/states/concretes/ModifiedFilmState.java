package com.pau_pau.project.models.states.concretes;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.states.Commentable;
import com.pau_pau.project.models.states.FilmState;
import com.pau_pau.project.models.states.FilmStatus;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("modified")
public class ModifiedFilmState extends FilmState implements Commentable {

    @Column
    String comment = null;

    public ModifiedFilmState() {
    }

    public ModifiedFilmState(FilmState oldState) {
        super(oldState);
    }

    public ModifiedFilmState(FilmState oldState, String comment) {
        super(oldState);
        this.comment = comment;
    }

    @Override
    public void publish(Account account) {
        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new ApprovedFilmState(film.getState()));
        }
    }

    @Override
    public void reject(Account account, String comment) {
        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new RejectedFilmState(film.getState(), comment));
        }
    }

    @Override
    public FilmStatus getStatusName() {
        return FilmStatus.MODIFIED;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getComment() {
        return comment;
    }
}
