package com.pau_pau.project.models.history;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.films.Film;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "account_film_id")
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

    @Column(name = "order")
    private int order;

    public History(){

    }

    public History(Account account, Film film, int order){
        this.account = account;
        this.film = film;
        this.order = order;
    }

    public static History containsInHistorySet(Set<History> historySet, Film film){
        for (History history:historySet){
            if (history.getFilm().equals(film))
                return history;
        }
        return null;
    }

    public static History getHistoryWithMaxOrder(Set<History> historySet){
        for (History history:historySet){
            if (history.getOrder() == ControllerConstants.MAX_HISTORY_SIZE){
                return history;
            }
        }
        return null;
    }

    public static void incAllGreaterOrder(Set<History> historySet, int limit){
        for (History history:historySet){
            int order = history.getOrder();
            if (order > limit){
                history.setOrder(order + 1);
            }
        }
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

  /*  public int getFilm_id() {
        return film_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }
*/
}
