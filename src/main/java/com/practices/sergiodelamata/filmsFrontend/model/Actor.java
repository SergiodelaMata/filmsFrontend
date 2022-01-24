package com.practices.sergiodelamata.filmsFrontend.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Actor {
    private Integer idActor;
    private String name;
    private Date birthDate;
    private String countryBirth;
    private List<Film> films = new ArrayList<>();

    public Actor() {
        this.idActor = 0;
        this.name = "";
        this.birthDate = new Date();
        this.countryBirth = "";
        this.films = null;
    }

    public Actor(Integer idActor, String name, Date birthDate, String countryBirth, List<Film> films) {
        this.idActor = idActor;
        this.name = name;
        this.birthDate = birthDate;
        this.countryBirth = countryBirth;
        this.films = films;
    }

    public Integer getIdActor() {
        return idActor;
    }

    public void setIdActor(Integer idActor) {
        this.idActor = idActor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountryBirth() {
        return countryBirth;
    }

    public void setCountryBirth(String countryBirth) {
        this.countryBirth = countryBirth;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
