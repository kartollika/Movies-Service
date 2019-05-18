package com.pau_pau.project.models.states;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.films.Film;

import javax.naming.NoPermissionException;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "state_type")
@Table(name = "states")
public abstract class FilmState {

    @Id
    @Column
    @GeneratedValue
    int id;

    @Column(name = "whose_id")
    int ownerId;

    @OneToOne(mappedBy = "state", cascade = CascadeType.ALL)
    protected Film film;

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (this.getClass() != other.getClass()) return false;
        FilmState otherObj = (FilmState) other;
        return this.film.getTitle().equals(otherObj.film.getTitle()) && this.id == otherObj.id
                && this.ownerId == otherObj.ownerId;
    }

    @Override
    public int hashCode()
    {
        return 76+133*id;
    }


    public FilmState() {
    }

    public FilmState(int ownerId) {
        this.ownerId = ownerId;
    }

    public FilmState(Account account) {
        ownerId = account.getId();
    }

    public FilmState(FilmState oldState) {
        id = oldState.id;
        ownerId = oldState.ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public abstract void publish(Account account) throws Exception;

    public abstract void reject(Account account, String comment) throws NoPermissionException;

    public abstract FilmStatus getStatusName();

    protected void causeDeniedException() throws NoPermissionException {
        throw new NoPermissionException("Denied");
    }

    protected void causeDeadEndException() {
        throw new DeadEndStateException("Not available to change state");
    }
}
