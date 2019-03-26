package com.pau_pau.project.models;

public class AccountDto {

    private String name;
    private String username;
    private String password;
    private Role permissionsLevel;

    public static AccountDto dtoFromAccount(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.name = account.getName();
        accountDto.username = account.getUsername();
        accountDto.permissionsLevel = account.getPermissionsLevel();
        return accountDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Role getPermissionsLevel() {
        return permissionsLevel;
    }

    public void setPermissionsLevel(Role permissionsLevel) {
        this.permissionsLevel = permissionsLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
