package com.pau_pau.project.models.history;

import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.films.Film;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "history", schema = "public")
public class History implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private int historyId;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "id")
    private Film film;

    @Column(name = "film_order")
    private int filmOrder;

    public History(){

    }

    public History(Account account, Film film, int filmOrder){
        this.account = account;
        this.film = film;
        this.filmOrder = filmOrder;
    }

    public static History getHistoryWithMinOrder(Set<History> historySet){
        for (History history:historySet){
            if (history.getFilmOrder() == 1){
                return history;
            }
        }
        return null;
    }


    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getFilmOrder() {
        return filmOrder;
    }

    public void setFilmOrder(int filmOrder) {
        this.filmOrder = filmOrder;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }
}
