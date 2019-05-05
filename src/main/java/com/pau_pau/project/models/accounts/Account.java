package com.pau_pau.project.models.accounts;

import com.pau_pau.project.models.films.Film;

import javax.persistence.*;
import java.util.List;

import static com.pau_pau.project.data.controllers.ControllerConstants.MAX_HISTORY_SIZE;

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

    @ManyToMany
    @JoinTable(
            name = "history",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private List<Film> history;

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

    public List<Film> getHistory() {
        return history;
    }

    public void setHistory(List<Film> history) {
        this.history = history;
    }

    public void addFilmToHistory(Film film){
        int size = history.size();
        if (history.contains(film)){
            history.remove(film);
        }
        history.add(0, film);
        if (size == MAX_HISTORY_SIZE){
            history.remove(size - 1);
        }
    }
}
