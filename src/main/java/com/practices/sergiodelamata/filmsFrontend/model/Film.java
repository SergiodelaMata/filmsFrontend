package com.practices.sergiodelamata.filmsFrontend.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Film {
    private Integer idFilm;
    private String title;
    private Integer year;
    private Integer duration;
    private String country;
    private String direction;
    private String genres;
    private String synopsis;
    private String image;
    private List<Actor> actors = new ArrayList<>();

    public Film() {
    }

    public Film(Integer idFilm, String title, Integer year, Integer duration, String country, String direction, String genres, String synopsis, String image, List<Actor> actors) {
        this.idFilm = idFilm;
        this.title = title;
        this.year = year;
        this.duration = duration;
        this.country = country;
        this.direction = direction;
        this.genres = genres;
        this.synopsis = synopsis;
        this.image = image;
        this.actors = actors;
    }

    public Integer getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Integer idFilm) {
        this.idFilm = idFilm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Page<Film> getPageFilm(List<Film> listFilms, Pageable pageable)
    {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Film> list;
        if(listFilms.size() < startItem)
        {
            list = Collections.emptyList();
        }
        else
        {
            int toIndex = Math.min(startItem + pageSize, listFilms.size());
            list = listFilms.subList(startItem, toIndex);
        }
        Page<Film> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), listFilms.size());
        return page;
    }

    public Film getFilmFromListByIdFilm(List<Film> listFilms, Integer idFilm){
        Film film = null;
        for(int i = 0; i < listFilms.size(); i++)
        {
            if(listFilms.get(i).getIdFilm() == idFilm)
            {
                film = listFilms.get(i);
            }
        }
        return film;
    }
}
