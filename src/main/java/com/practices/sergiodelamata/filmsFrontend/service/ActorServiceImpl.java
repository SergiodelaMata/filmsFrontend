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

    String urlActors = "http://localhost:8090/api/films-actors/actors";

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
        Actor actor = new Actor();
        List<Actor> listActors = Arrays.asList(actors);
        Page<Actor> page = actor.getPageActor(listActors, pageable);
        return page;
    }

    @Override
    public Page<Film> searchFilmsByActorsName(String name, Pageable pageable)
    {
        Actor[] actors = template.getForObject(urlActors + "/name/" + name, Actor[].class);
        List<Actor> listActors = Arrays.asList(actors);
        ArrayList<Actor> arrayListActor = new ArrayList<>(listActors);
        ArrayList<Film> arrayListFilms = new ArrayList<>();
        ArrayList<String> arrayListFilmsName = new ArrayList<>();
        for(int i = 0; i < arrayListActor.size(); i++)
        {
            List<Film> films = arrayListActor.get(i).getFilms();
            for(int j = 0; j < films.size(); j++)
            {
                if(!arrayListFilmsName.contains(films.get(j).getTitle())) //Introduce a la lista la película si ya no se encontraba antes
                {
                    arrayListFilms.add(films.get(j));
                    arrayListFilmsName.add(films.get(j).getTitle());
                }
            }
        }
        Film film = new Film();
        List<Film> listFilms = arrayListFilms;
        Page<Film> page = film.getPageFilm(listFilms, pageable);
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

    @Override
    /*
     * Devuelve el listado de todos los actores que no se encuentran en el reparto de una película
     */
    public List<Actor> searchActorsNotInFilm(Film film) {
        Actor[] actors = template.getForObject(urlActors, Actor[].class);
        List<Actor> listActors = film.getActors();
        ArrayList<Integer> listIdActors = new ArrayList<>();
        for(int i = 0; i < listActors.size(); i++)
        {
            listIdActors.add(listActors.get(i).getIdActor());
        }
        List<Actor> completeListActors = Arrays.asList(actors);
        ArrayList<Integer> completeListIdActors = new ArrayList<>();
        for(int i = 0; i < completeListActors.size(); i++)
        {
            completeListIdActors.add(completeListActors.get(i).getIdActor());
        }
        ArrayList<Actor> listActorsAux = new ArrayList<>();
        for(int i = 0; i < completeListActors.size(); i++)
        {
            //Comprueba si el identificador del actor comparado ya se encuentra en la lista de los nombres de los actores que
            // ya hay en la película antes de introducirlo en la lista auxiliar
            if(!listIdActors.contains(completeListIdActors.get(i)))
            {
                listActorsAux.add(completeListActors.get(i));
            }
        }
        return listActorsAux;
    }

}
