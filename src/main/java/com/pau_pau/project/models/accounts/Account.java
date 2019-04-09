package com.pau_pau.project.models.accounts;

import javax.persistence.*;

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
}
