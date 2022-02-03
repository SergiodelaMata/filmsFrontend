package com.practices.sergiodelamata.filmsFrontend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private Integer idUser;
    private String username;
    private String password;
    private String email;
    private Boolean enable;
    private List<Rol> rols = new ArrayList<>();

    public User(){}

    public User(Integer idUser, String username, String password, String email, Boolean enable, List<Rol> rols, List<Critic> critics) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enable = enable;
        this.rols = rols;
        this.critics = critics;
    }

    private List<Critic> critics = new ArrayList<>();

    public List<Critic> getCritics() {
        return critics;
    }

    public void setCritics(List<Critic> critics) {
        this.critics = critics;
    }

    public List<Rol> getRols() {
        return rols;
    }

    public void setRols(List<Rol> rols) {
        this.rols = rols;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(idUser, user.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }


}
