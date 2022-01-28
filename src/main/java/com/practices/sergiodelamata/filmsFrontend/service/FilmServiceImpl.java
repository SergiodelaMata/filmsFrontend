package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Film;
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
public class FilmServiceImpl implements IFilmService{
    @Autowired
    RestTemplate template;

    String url = "http://localhost:8001/films";

    @Override
    public Page<Film> searchAll(Pageable pageable) {
        Film[] films = template.getForObject(url, Film[].class);
        List<Film> listFilms = Arrays.asList(films);

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

    @Override
    public Film searchFilmById(Integer idFilm) {
        Film film = template.getForObject(url + "/" + idFilm, Film.class);
        return film;
    }

    @Override
    public Page<Film> searchFilmsByTitle(String title, Pageable pageable) {
        Film[] films = template.getForObject(url + "/title/" + title, Film[].class);
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = new PageImpl<>(listFilms, pageable, listFilms.size());
        return page;
    }

    @Override
    public Page<Film> searchFilmsByYear(Integer yearInit, Integer yearEnd, Pageable pageable) {
        Film[] films = template.getForObject(url + "/year/" + yearInit + "/" + yearEnd, Film[].class);
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = new PageImpl<>(listFilms, pageable, listFilms.size());
        return page;
    }

    @Override
    public Page<Film> searchFilmsByCountry(String country, Pageable pageable) {
        Film[] films = template.getForObject(url + "/country/" + country, Film[].class);
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = new PageImpl<>(listFilms, pageable, listFilms.size());
        return page;
    }

    @Override
    public Page<Film> searchFilmsByDirection(String direction, Pageable pageable) {
        Film[] films = template.getForObject(url + "/direction/" + direction, Film[].class);
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = new PageImpl<>(listFilms, pageable, listFilms.size());
        return page;
    }

    @Override
    public Page<Film> searchFilmsByGenres(String genres, Pageable pageable) {
        Film[] films = template.getForObject(url + "/genres/" + genres, Film[].class);
        List<Film> listFilms = Arrays.asList(films);
        Page<Film> page = new PageImpl<>(listFilms, pageable, listFilms.size());
        return page;
    }

    @Override
    public void saveFilm(Film film) {
        if(film.getIdFilm() != null && film.getIdFilm() > 0)
        {
            template.put(url, film);
        }
        else
        {
            template.postForObject(url, film, String.class);
        }
    }

    @Override
    public void deleteFilm(Integer idFilm) {
        template.delete(url + "/" + idFilm);
    }

    @Override
    public void insertActor(Integer idFilm, Integer idActor) {
        template.put(url + "/insert/actor/" + idFilm + "/" + idActor, String.class);
    }

    @Override
    public void removeActor(Integer idFilm, Integer idActor) {
        System.out.println(url + "/delete/actor/" + idFilm + "/" + idActor);
        template.delete(url + "/delete/actor/" + idFilm + "/" + idActor, String.class);
    }
}
