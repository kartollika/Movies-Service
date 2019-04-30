package com.pau_pau.project.models.states.concretes;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.states.Commentable;
import com.pau_pau.project.models.states.FilmState;
import com.pau_pau.project.models.states.FilmStatus;

import javax.naming.NoPermissionException;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("rejected")
public class RejectedFilmState extends FilmState implements Commentable {

    @Column
    private String comment = null;

    public RejectedFilmState() {
    }

    RejectedFilmState(FilmState oldState) {
        super(oldState);
    }

    public RejectedFilmState(FilmState oldState, String comment) {
        super(oldState);
        this.comment = comment;
    }

    @Override
    public void publish(Account account) throws NoPermissionException {
        if (account.getPermissionsLevel().equals(Role.EDITOR)) {
            film.setState(new ModifiedFilmState(film.getState()));
            return;
        }

        if (account.getPermissionsLevel().equals(Role.ADMIN)) {
            film.setState(new ApprovedFilmState(film.getState()));
            return;
        }

        causeDeniedException();
    }

    @Override
    public void reject(Account account, String comment) {
        causeDeadEndException();
    }

    @Override
    public FilmStatus getStatusName() {
        return FilmStatus.REJECTED;
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
