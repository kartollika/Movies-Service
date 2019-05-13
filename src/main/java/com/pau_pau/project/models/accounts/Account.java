package com.pau_pau.project.models.accounts;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.history.History;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "permissions_level")
    private Role permissionsLevel;

    @ManyToMany
    @JoinTable(
        name = "wishlist",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private List<Film> wishlist;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<History> historySet = new HashSet<>();

    public Set<History> getHistorySet() {
        return historySet;
    }


    public void addFilmToHistory(Film film){
        History history = History.containsInHistorySet(historySet, film);

        if (history == null) {
            int setSize = historySet.size();
            if (setSize < ControllerConstants.MAX_HISTORY_SIZE){
                historySet.add(new History(this, film, setSize + 1));
            }else{
                historySet.remove(History.getHistoryWithMaxOrder(historySet));
                History.incAllGreaterOrder(historySet, setSize + 1);
                historySet.add(new History(this, film, 1));
            }
        }else{
            historySet.remove(history);
            int order = history.getOrder();
            history.setOrder(1);
            History.incAllGreaterOrder(historySet, order);
            historySet.add(history);
        }
    }

    /*@ManyToMany
    @JoinTable(
            name = "historySet",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
//            inverseJoinColumns = {@JoinColumn(name = "film_id"), @JoinColumn(name = "order")}
//            inverseJoinColumns = @JoinColumns(value = {@JoinColumn(name = "film_id"),
//            @JoinColumn(name = "order")})
    )
    private List<Film> historySet;*/

    public Account() {
        super();
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nname:  " + name +
                "\nusername: " + username +
                "\npassword: Not allowed" +
                "\npermissionLevel: " + permissionsLevel;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getPermissionsLevel() {
        return permissionsLevel;
    }

    public void setPermissionsLevel(Role permissionsLevel) {
        this.permissionsLevel = permissionsLevel;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public List<Film> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Film> wishlist) {
        this.wishlist = wishlist;
    }

    /*public List<Film> getHistorySet() {
        return historySet;
    }

    public void setHistory(List<Film> historySet) {
        this.historySet = historySet;
    }

    public void addFilmToHistory(Film film){
        int size = historySet.size();
        if (historySet.contains(film)){
            historySet.remove(film);
        }
        historySet.add(0, film);
        if (size == MAX_HISTORY_SIZE){
            historySet.remove(size - 1);
        }
    }*/
}
