package com.practices.sergiodelamata.filmsFrontend.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Actor {
    private Integer idActor;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String countryBirth;
    private String image;
    private List<Film> films = new ArrayList<>();

    public Actor() {
        this.idActor = 0;
        this.name = "";
        this.birthDate = new Date();
        this.countryBirth = "";
        this.films = null;
    }

    public Actor(Integer idActor, String name, Date birthDate, String countryBirth, String image, List<Film> films) {
        this.idActor = idActor;
        this.name = name;
        this.birthDate = birthDate;
        this.countryBirth = countryBirth;
        this.image = image;
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

    public String getBirthDateFormat()
    {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(this.birthDate);
        return date;
    }

    public String getCountryBirth() {
        return countryBirth;
    }

    public void setCountryBirth(String countryBirth) {
        this.countryBirth = countryBirth;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public Page<Actor> getPageActor(List<Actor> listActors, Pageable pageable)
    {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Actor> list;
        if(listActors.size() < startItem)
        {
            list = Collections.emptyList();
        }
        else
        {
            int toIndex = Math.min(startItem + pageSize, listActors.size());
            list = listActors.subList(startItem, toIndex);
        }
        Page<Actor> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), listActors.size());
        return page;
    }

}
