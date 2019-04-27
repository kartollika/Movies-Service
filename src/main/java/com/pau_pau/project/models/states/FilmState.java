package com.pau_pau.project.models.states;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.films.Film;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "state_type")
@Table(name = "states")
public abstract class FilmState {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "whose_id")
    int ownerId;

    @OneToOne(mappedBy = "state")
    protected Film film;

    public FilmState() {
    }

    public FilmState(int ownerId) {
        this.ownerId = ownerId;
    }

    public FilmState(Account account) {
        ownerId = account.getId();
    }

    public FilmState(FilmState oldState) {
        ownerId = oldState.ownerId;
    }

    public abstract void publish(Account account) throws Exception;

    public abstract void reject(Account account);

    public abstract FilmStatus getStatusName();
}
