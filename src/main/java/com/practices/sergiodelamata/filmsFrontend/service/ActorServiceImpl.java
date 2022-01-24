package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ActorServiceImpl implements IActorService {
    @Autowired
    RestTemplate template;

    String url = "http://localhost:8001/actors";

    @Override
    public Page<Actor> searchAll(Pageable pageable) {
        Actor[] actors = template.getForObject(url, Actor[].class);
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
        Actor actor = template.getForObject(url + "/" + idActor, Actor.class);
        return actor;
    }

    @Override
    public Page<Actor> searchActorsByName(String name, Pageable pageable) {
        Actor[] actors = template.getForObject(url + "/name/" + name, Actor[].class);
        List<Actor> listActors = Arrays.asList(actors);
        Page<Actor> page = new PageImpl<>(listActors, pageable, listActors.size());
        return page;
    }

    @Override
    public void saveActor(Actor actor) {
        if(actor.getIdActor() != null && actor.getIdActor() > 0)
        {
            template.put(url, actor);
        }
        else
        {
            template.postForObject(url, actor, String.class);
        }
    }

    @Override
    public void deleteActor(Integer idActor) {
        template.delete(url + "/" + idActor);
    }

    @Override
    public void insertFilm(Integer idActor, Integer idFilm) {
        template.put(url + "/insert/film/" + idActor + "/" + idFilm, String.class);
    }
}
