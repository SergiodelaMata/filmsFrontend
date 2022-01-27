package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Actor;
import com.practices.sergiodelamata.filmsFrontend.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActorService {
    Page<Actor> searchAll(Pageable pageable);

    Actor searchActorById(Integer idActor);

    Page<Actor> searchActorsByName(String name, Pageable pageable);

    Page<Film> searchFilmsByActorsName(String name, Pageable pageable);

    void saveActor(Actor actor);

    void deleteActor(Integer idActor);

    void insertFilm(Integer idActor, Integer idFilm);
}
