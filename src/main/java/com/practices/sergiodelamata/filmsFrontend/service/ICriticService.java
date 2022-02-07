package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Critic;
import com.practices.sergiodelamata.filmsFrontend.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICriticService {
    Page<Critic> searchAll(Pageable pageable);

    Critic searchCriticById(Integer idCritic);

    List<Critic> searchCriticByIdFilm(Integer idFilm);

    Page<Critic> searchCriticByIdFilms(List<Film> listFilms, Pageable pageable);

    void saveCritic(Critic critic);

    void deleteCritic(Integer idCritic);

}
