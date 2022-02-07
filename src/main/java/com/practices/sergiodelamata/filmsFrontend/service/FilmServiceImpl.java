package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Actor;
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
public class FilmServiceImpl implements IFilmService{
    @Autowired
    RestTemplate template;

    String urlFilms = "http://localhost:8090/api/films-actors/films";

    @Override
    public List<Film> searchAll()
    {
        Film[] films = template.getForObject(urlFilms, Film[].class);
        List<Film> listFilms = Arrays.asList(films);
        return listFilms;
    }

    @Override
    public Page<Film> searchAll(Pageable pageable) {
        Film[] films = template.getForObject(urlFilms, Film[].class);
        Film film = new Film();
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = film.getPageFilm(listFilms, pageable);
        return page;
    }

    @Override
    public Film searchFilmById(Integer idFilm) {
        Film film = template.getForObject(urlFilms + "/" + idFilm, Film.class);
        return film;
    }

    @Override
    public List<Film> searchFilmsByTitle(String title)
    {
        Film[] films = template.getForObject(urlFilms + "/title/" + title, Film[].class);
        List<Film> listFilms = Arrays.asList(films);
        return listFilms;
    }

    @Override
    public Page<Film> searchFilmsByTitle(String title, Pageable pageable) {
        Film[] films = template.getForObject(urlFilms + "/title/" + title, Film[].class);
        Film film = new Film();
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = film.getPageFilm(listFilms, pageable);
        return page;
    }

    @Override
    public Page<Film> searchFilmsByYear(Integer yearInit, Integer yearEnd, Pageable pageable) {
        Film[] films = template.getForObject(urlFilms + "/year/" + yearInit + "/" + yearEnd, Film[].class);
        Film film = new Film();
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = film.getPageFilm(listFilms, pageable);
        return page;
    }

    @Override
    public Page<Film> searchFilmsByCountry(String country, Pageable pageable) {
        Film[] films = template.getForObject(urlFilms + "/country/" + country, Film[].class);
        Film film = new Film();
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = film.getPageFilm(listFilms, pageable);
        return page;
    }

    @Override
    public Page<Film> searchFilmsByDirection(String direction, Pageable pageable) {
        Film[] films = template.getForObject(urlFilms + "/direction/" + direction, Film[].class);
        Film film = new Film();
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = film.getPageFilm(listFilms, pageable);
        return page;
    }

    @Override
    public Page<Film> searchFilmsByGenres(String genres, Pageable pageable) {
        Film[] films = template.getForObject(urlFilms + "/genres/" + genres, Film[].class);
        Film film = new Film();
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = film.getPageFilm(listFilms, pageable);
        return page;
    }

    @Override
    public Page<Actor> searchActorsByFilmsTitle(String title, Pageable pageable)
    {
        Film[] films = template.getForObject(urlFilms + "/title/" + title, Film[].class);
        List<Film> listFilms = Arrays.asList(films);
        ArrayList<Film> arrayListFilms = new ArrayList<>(listFilms);
        ArrayList<Actor> arrayListActor = new ArrayList<>();
        ArrayList<String> arrayListActorName = new ArrayList<>();
        for(int i = 0; i < arrayListFilms.size(); i++)
        {
            List<Actor> actors = arrayListFilms.get(i).getActors();
            for(int j = 0; j < actors.size(); j++)
            {
                if(!arrayListActorName.contains(actors.get(j).getName())) //Introduce a la lista el actor si ya no se encontraba antes
                {
                    arrayListActor.add(actors.get(j));
                    arrayListActorName.add(actors.get(j).getName());
                }
            }
        }
        Actor actor = new Actor();
        List<Actor> listActors = arrayListActor;
        Page<Actor> page = actor.getPageActor(listActors, pageable);
        return page;
    }

    @Override
    public void saveFilm(Film film) {
        if(film.getIdFilm() != null && film.getIdFilm() > 0)
        {
            template.put(urlFilms, film);
        }
        else
        {
            template.postForObject(urlFilms, film, String.class);
        }
    }

    @Override
    public void deleteFilm(Integer idFilm) {
        template.delete(urlFilms + "/" + idFilm);
    }

    @Override
    public void insertActor(Integer idFilm, Integer idActor) {
        template.put(urlFilms + "/insert/actor/" + idFilm + "/" + idActor, String.class);
    }

    @Override
    public void removeActor(Integer idFilm, Integer idActor) {
        template.delete(urlFilms + "/delete/actor/" + idFilm + "/" + idActor, String.class);
    }

    @Override
    /*
     * Devuelve el listado de todas las películas en las que un actor no participa
     */
    public List<Film> searchFilmsNotInActor(Actor actor) {
        Film[] films = template.getForObject(urlFilms, Film[].class);
        List<Film> listFilms = actor.getFilms();
        ArrayList<Integer> listIdFilms = new ArrayList<>();
        for(int i = 0; i < listFilms.size(); i++)
        {
            listIdFilms.add(listFilms.get(i).getIdFilm());
        }
        List<Film> completeListFilms = Arrays.asList(films);
        ArrayList<Integer> completeListIdFilms = new ArrayList<>();
        for(int i = 0; i < completeListFilms.size(); i++)
        {
            completeListIdFilms.add(completeListFilms.get(i).getIdFilm());
        }
        ArrayList<Film> listFilmsAux = new ArrayList<>();
        for(int i = 0; i < completeListFilms.size(); i++)
        {
            //Comprueba si el identificador de la película comparada ya se encuentra en la lista de los títulos de las películas que
            // ya tiene el actor antes de introducirlo en la lista auxiliar
            if(!listIdFilms.contains(completeListIdFilms.get(i)))
            {
                listFilmsAux.add(completeListFilms.get(i));
            }
        }
        return listFilmsAux;
    }

    @Override
    public List<Film> searchFilmsNotWithCriticFromUser(User user)
    {
        Film[] films = template.getForObject(urlFilms, Film[].class);
        ArrayList<Film> completeListFilm = new ArrayList<Film>(Arrays.asList(films));
        List<Critic> listCritics = user.getCritics();
        List<Film> listFilmAux = new ArrayList<>();

        for(int i = 0; i < completeListFilm.size(); i++)
        {
            for(int j = 0; j < listCritics.size(); j++)
            {
                if(Objects.equals(completeListFilm.get(i).getIdFilm(), listCritics.get(j).getIdFilm()))
                {
                    listFilmAux.add(completeListFilm.get(i));
                }
            }
        }

        for(int i = completeListFilm.size()-1; i >= 0; i--)
        {
            for(int j = 0; j < listFilmAux.size(); j++)
            {
                if(Objects.equals(completeListFilm.get(i).getIdFilm(), listFilmAux.get(j).getIdFilm()))
                {
                    completeListFilm.remove(i);
                }
            }
        }

        return completeListFilm;
    }


}
