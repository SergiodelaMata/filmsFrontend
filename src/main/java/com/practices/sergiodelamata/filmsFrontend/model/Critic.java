package com.practices.sergiodelamata.filmsFrontend.model;

import java.util.Date;
import java.util.Objects;

public class Critic {
    private Integer idCritic;
    private Integer idFilm;
    private User idUser;
    private String valoration;
    private Integer mark;
    private Date dateCritic;

    public Critic() {}

    public Critic(Integer idCritic, Integer idFilm, User idUser, String valoration, Integer mark, Date dateCritic) {
        this.idCritic = idCritic;
        this.idFilm = idFilm;
        this.idUser = idUser;
        this.valoration = valoration;
        this.mark = mark;
        this.dateCritic = dateCritic;
    }

    public Date getDateCritic() {
        return dateCritic;
    }

    public void setDateCritic(Date dateCritic) {
        this.dateCritic = dateCritic;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getValoration() {
        return valoration;
    }

    public void setValoration(String valoration) {
        this.valoration = valoration;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Integer getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Integer idFilm) {
        this.idFilm = idFilm;
    }

    public Integer getIdCritic() {
        return idCritic;
    }

    public void setIdCritic(Integer idCritic) {
        this.idCritic = idCritic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        Critic critic = (Critic) o;
        return Objects.equals(idCritic, critic.idCritic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCritic);
    }

}
