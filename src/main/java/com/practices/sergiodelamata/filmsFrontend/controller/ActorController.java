package com.practices.sergiodelamata.filmsFrontend.controller;

import com.practices.sergiodelamata.filmsFrontend.model.Actor;
import com.practices.sergiodelamata.filmsFrontend.model.Film;
import com.practices.sergiodelamata.filmsFrontend.paginator.PageRender;
import com.practices.sergiodelamata.filmsFrontend.service.IActorService;
import com.practices.sergiodelamata.filmsFrontend.service.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/actors")
public class ActorController {
    @Autowired
    IFilmService filmService;
    @Autowired
    IActorService actorService;

    @GetMapping(value = {"/", ""})
    public String home(Model model, @RequestParam(name="page", defaultValue = "0") int page)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> list = actorService.searchAll(pageable);
        PageRender<Actor> pageRender = new PageRender<Actor>("/actors", list);

        model.addAttribute("title", "FilmingApp | Listado de actores");
        model.addAttribute("listActors", list);
        model.addAttribute("page", pageRender);
        return "actors";
    }

    @GetMapping("/new")
    public String newActor(Model model)
    {
        Actor actor = new Actor();
        model.addAttribute("title", "Nuevo actor");
        model.addAttribute("actor", actor);
        return "actors/formActorNew";
    }

    @GetMapping("/{idActor}")
    public String searchActorById(Model model, @PathVariable("idActor") Integer idActor,
                                  @RequestParam(name="mode", defaultValue = "request") String mode)
    {
        Actor actor = actorService.searchActorById(idActor);
        model.addAttribute("title", "FilmingApp | Consultar datos de actor");
        model.addAttribute("mode", mode);
        model.addAttribute("header", "Consultar datos de actor");
        model.addAttribute("actor", actor);
        return "actors/formActor";
    }

    @GetMapping("/name")
    public String searchActorByName(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                    @RequestParam("name") String name,
                                    @RequestParam(name="typeSearch", defaultValue = "name") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> list;
        if(name.equals(""))
        {
            list = actorService.searchAll(pageable);
        }
        else
        {
            list = actorService.searchActorsByName(name, pageable);
        }
        PageRender<Actor> pageRender = new PageRender<Actor>("/actors/name?name=" + name + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de actores por nombre");
        model.addAttribute("listActors", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "actors/listActors";
    }

    @GetMapping("/filmTitle")
    public String searchActorByFilmTitle(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                    @RequestParam("filmTitle") String filmTitle,
                                    @RequestParam(name="typeSearch", defaultValue = "filmTitle") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> list;
        if(filmTitle.equals(""))
        {
            list = actorService.searchAll(pageable);
        }
        else
        {
            list = filmService.searchActorsByFilmsTitle(filmTitle, pageable);
        }
        PageRender<Actor> pageRender = new PageRender<Actor>("/actors/filmTitle?filmTitle=" + filmTitle + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de actores por título de película");
        model.addAttribute("listActors", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "actors/listActors";
    }

    @GetMapping("/film/{idActor}")
    public String insertActorToFilm(Model model, @PathVariable("idActor") Integer idActor){
        Actor actor = actorService.searchActorById(idActor);
        List<Film> listFilmNotMadeByActor = filmService.searchFilmsNotInActor(actor);
        model.addAttribute("title", "FilmingApp | Nueva película para el actor");
        model.addAttribute("idActor", idActor);
        model.addAttribute("listFilms", listFilmNotMadeByActor);

        return "actors/formAddFilmActor";
    }

    @PostMapping("/save")
    public String saveActor(Model model, @RequestBody Actor actor, RedirectAttributes attributes)
    {
        actorService.saveActor(actor);
        model.addAttribute("title", "Nuevo actor");
        attributes.addFlashAttribute("msg", "Los datos del actor se han guardado correctamente.");
        return "actors";
    }

    @PutMapping ("/save")
    public String updateFilm(Model model, @RequestBody Actor actor, RedirectAttributes attributes)
    {
        Actor actorAux = actorService.searchActorById(actor.getIdActor());
        actor.setFilms(actorAux.getFilms());
        actorService.saveActor(actor);
        model.addAttribute("title", "Actor actualizado");
        attributes.addFlashAttribute("msg", "Los datos del actor se han guardado correctamente.");
        return "films";
    }

    @GetMapping("/edit/{idActor}")
    public String editActor(Model model, @PathVariable("idActor") Integer idActor, @RequestParam(name="mode", defaultValue = "edit") String mode)
    {
        Actor actor = actorService.searchActorById(idActor);
        model.addAttribute("title", "FilmingApp | Editar datos del actor");
        model.addAttribute("mode", mode);
        model.addAttribute("header", "Editar datos de actor");
        model.addAttribute("actor", actor);
        return "actors/formActor";
    }

    @DeleteMapping("/delete/{idActor}")
    public String deleteActor(Model model, @PathVariable("idActor") Integer idActor, RedirectAttributes attributes)
    {
        actorService.deleteActor(idActor);
        attributes.addFlashAttribute("msg", "Se ha borrado el actor correctamente.");
        return "actors";
    }

    @PutMapping("/insert/film/{idActor}/{idFilm}")
    public String insertFilm(@PathVariable("idActor") Integer idActor, @PathVariable("idFilm") Integer idFilm){
        actorService.insertFilm(idActor, idFilm);
        return "actors";
    }

    @DeleteMapping("/delete/film/{idActor}/{idFilm}")
    public String deleteFilm(@PathVariable("idActor") Integer idActor, @PathVariable("idFilm") Integer idFilm){
        actorService.removeFilm(idActor, idFilm);
        return "actors";
    }

}
