package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Critic;
import com.practices.sergiodelamata.filmsFrontend.model.Film;
import com.practices.sergiodelamata.filmsFrontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CriticServiceImpl implements ICriticService{
    @Autowired
    RestTemplate template;

    @Autowired
    IUserService userService;

    @Autowired
    IFilmService filmService;


    String url = "http://localhost:8090/api/users-critics/critics";

    @Override
    public Page<Critic> searchAll(Pageable pageable) {
        Critic critic = new Critic();
        Critic[] critics = template.getForObject(url, Critic[].class);
        List<Critic> listCritics = Arrays.asList(critics);

        Page<Critic> page = critic.getPageCritic(listCritics, pageable);
        return page;
    }

    @Override
    public Critic searchCriticById(Integer idCritic) {
        Critic critic = template.getForObject(url + "/" + idCritic, Critic.class);
        return critic;
    }

    @Override
    public List<Critic> searchCriticByIdFilm(Integer idFilm){
        Critic[] critics = template.getForObject(url + "/film/" + idFilm, Critic[].class);
        List<Critic> listCritics = Arrays.asList(critics);
        return listCritics;
    }

    @Override
    public Page<Critic> searchCriticByIdFilms(List<Film> filmList, Pageable pageable) {
        List<Critic> listCritics = new ArrayList<>();
        for(int i = 0; i < filmList.size(); i++)
        {
            Critic[] critics = template.getForObject(url + "/film/" + filmList.get(i).getIdFilm(), Critic[].class);
            List<Critic> listCriticsAux = Arrays.asList(critics);
            listCritics.addAll(listCriticsAux);
        }

        Critic critic = new Critic();
        Page<Critic> page = critic.getPageCritic(listCritics, pageable);
        return page;
    }

    @Override
    public void saveCritic(Critic critic) {
        if (critic.getIdCritic() != null && critic.getIdCritic() > 0) {
            template.put(url, critic);
        } else {
            template.postForObject(url, critic, String.class);
        }
    }

    @Override
    public void deleteCritic(Integer idCritic) {
        template.delete(url + "/" + idCritic);
    }
}
