package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFilmService {
    Page<Film> searchAll(Pageable pageable);

    Film searchFilmById(Integer idFilm);

    Page<Film> searchFilmsByTitle(String title, Pageable pageable);

    Page<Film> searchFilmsByYear(Integer yearInit, Integer yearEnd, Pageable pageable);

    Page<Film> searchFilmsByCountry(String country, Pageable pageable);

    Page<Film> searchFilmsByDirection(String direction, Pageable pageable);

    Page<Film> searchFilmsByGenres(String genres, Pageable pageable);

    void saveFilm(Film film);

    void deleteFilm(Integer idFilm);

    void insertActor(Integer idFilm, Integer idActor);

    void removeActor(Integer idFilm, Integer idActor);

}
