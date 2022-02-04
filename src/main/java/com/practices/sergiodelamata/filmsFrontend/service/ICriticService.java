package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Critic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICriticService {
    Page<Critic> searchAll(Pageable pageable);

    Critic searchCriticById(Integer idCritic);

    Page<Critic> searchCriticByIdFilm(Integer idFilm, Pageable pageable);

    void saveCritic(Critic critic);

    void deleteCritic(Integer idCritic);

}
