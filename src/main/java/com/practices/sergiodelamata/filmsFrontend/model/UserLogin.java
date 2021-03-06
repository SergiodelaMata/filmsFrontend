package com.practices.sergiodelamata.filmsFrontend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserLogin {
    private Integer idUser;
    private String username;
    private String password;
    private String email;
    private Integer enable;
    private List<Role> roles = new ArrayList<>();

    public UserLogin(){}

    public UserLogin(Integer idUser, String username, String password, String email, Integer enable, List<Role> rols, List<Critic> critics) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enable = enable;
        this.roles = roles;
        this.critics = critics;
    }

    public UserLogin(User user)
    {
        this.idUser = user.getIdUser();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.enable = user.getEnable();
        this.roles = user.getRoles();
        this.critics = user.getCritics();
    }

    private List<Critic> critics = new ArrayList<>();

    public List<Critic> getCritics() {
        return critics;
    }

    public void setCritics(List<Critic> critics) {
        this.critics = critics;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLogin)) return false;
        UserLogin user = (UserLogin) o;
        return Objects.equals(idUser, user.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }


}
