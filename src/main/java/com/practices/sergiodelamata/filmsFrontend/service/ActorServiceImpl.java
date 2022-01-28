package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Actor;
import com.practices.sergiodelamata.filmsFrontend.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ActorServiceImpl implements IActorService {
    @Autowired
    RestTemplate template;

    String urlActors = "http://localhost:8001/actors";
    String urlFilms = "http://localhost:8001/films";

    @Override
    public Page<Actor> searchAll(Pageable pageable) {
        Actor[] actors = template.getForObject(urlActors, Actor[].class);
        List<Actor> listActors = Arrays.asList(actors);

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

    @Override
    public Actor searchActorById(Integer idActor) {
        Actor actor = template.getForObject(urlActors + "/" + idActor, Actor.class);
        return actor;
    }

    @Override
    public Page<Actor> searchActorsByName(String name, Pageable pageable) {
        Actor[] actors = template.getForObject(urlActors + "/name/" + name, Actor[].class);
        List<Actor> listActors = Arrays.asList(actors);
        Page<Actor> page = new PageImpl<>(listActors, pageable, listActors.size());
        return page;
    }

    @Override
    public Page<Film> searchFilmsByActorsName(String name, Pageable pageable)
    {
        Actor[] actors = template.getForObject(urlActors + "/name/" + name, Actor[].class);
        List<Actor> listActors = Arrays.asList(actors);
        ArrayList<Actor> arrayListActor = new ArrayList<>(listActors);
        ArrayList<Film> arrayListFilms = new ArrayList<>();
        for(int i = 0; i < arrayListActor.size(); i++)
        {
            List<Film> films = arrayListActor.get(i).getFilms();
            for(int j = 0; j < films.size(); j++)
            {
                if(!arrayListFilms.contains(films.get(j))) //Introduce a la lista la película si ya no se encontraba antes
                {
                    arrayListFilms.add(films.get(j));
                }
            }
        }
        List<Film> listFilms = arrayListFilms;
        Page<Film> page = new PageImpl<>(listFilms, pageable, listFilms.size());
        return page;
    }


    @Override
    public void saveActor(Actor actor) {
        if(actor.getIdActor() != null && actor.getIdActor() > 0)
        {
            template.put(urlActors, actor);
        }
        else
        {
            template.postForObject(urlActors, actor, String.class);
        }
    }

    @Override
    public void deleteActor(Integer idActor) {
        template.delete(urlActors + "/" + idActor);
    }

    @Override
    public void insertFilm(Integer idActor, Integer idFilm) {
        template.put(urlActors + "/insert/film/" + idActor + "/" + idFilm, String.class);
    }

    @Override
    public void removeFilm(Integer idActor, Integer idFilm) {
        template.put(urlActors + "/delete/film/" + idActor + "/" + idFilm, String.class);
    }

}
