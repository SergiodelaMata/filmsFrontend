package com.practices.sergiodelamata.filmsFrontend.controller;

import com.practices.sergiodelamata.filmsFrontend.model.Actor;
import com.practices.sergiodelamata.filmsFrontend.paginator.PageRender;
import com.practices.sergiodelamata.filmsFrontend.service.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/actors")
public class ActorController {
    @Autowired
    IActorService actorService;

    @GetMapping (value = {"/", "/home", ""})
    public String home(Model model)
    {
        return "home";
    }

    @GetMapping("/new")
    public String newActor(Model model)
    {
        model.addAttribute("title", "Nuevo actor");
        Actor actor = new Actor();
        model.addAttribute("actor", actor);
        return "actors/formActor";
    }

    @GetMapping("/search")
    public String searchActor(Model model)
    {
        return "actors/searchActor";
    }

    @GetMapping("/list")
    public String listActors(Model model, @RequestParam(name="page", defaultValue = "0") int page)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> list = actorService.searchAll(pageable);
        PageRender<Actor> pageRender = new PageRender<Actor>("/actors/list", list);

        model.addAttribute("title", "Listado de actores");
        model.addAttribute("listActors", list);
        return "actors/listActors";
    }

    @GetMapping("/{idActor}")
    public String searchActorById(Model model, @PathVariable("idActor") Integer idActor)
    {
        Actor actor = actorService.searchActorById(idActor);
        model.addAttribute("actor", actor);
        return "actors/formActor";
    }

    @GetMapping("/name")
    public String searchActorByName(Model model, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam("name") String name)
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
        PageRender<Actor> pageRender = new PageRender<Actor>("/list", list);
        model.addAttribute("title", "Listado de actores por nombre");
        model.addAttribute("listActors", list);
        model.addAttribute("page", pageRender);
        return "actors/listActors";
    }

    @PostMapping("/save")
    public String saveActor(Model model, Actor actor, RedirectAttributes attributes)
    {
        actorService.saveActor(actor);
        model.addAttribute("title", "Nuevo actor");
        attributes.addFlashAttribute("msg", "Los datos del actor se han guardado correctamente.");
        return "redirect:/actors/list";
    }

    @GetMapping("/edit/{idActor}")
    public String editActor(Model model, @PathVariable("idActor") Integer idActor)
    {
        Actor actor = actorService.searchActorById(idActor);
        model.addAttribute("title", "Editar datos del actor");
        model.addAttribute("actor", actor);
        return "actors/formActor";
    }

    @GetMapping("/delete/{idActor}")
    public String deleteActor(Model model, @PathVariable("idActor") Integer idActor, RedirectAttributes attributes)
    {
        actorService.deleteActor(idActor);
        attributes.addFlashAttribute("msg", "Se ha borrado el actor correctamente.");
        return "redirect:/actors/list";
    }

    @PutMapping("/insert/film/{idActor}/{idFilm}")
    public String insertFilm(@PathVariable("idActor") Integer idActor, @PathVariable("idFilm") Integer idFilm){
        actorService.insertFilm(idActor, idFilm);
        return "redirect:/actors/list";
    }

    @DeleteMapping("/delete/film/{idActor}/{idFilm}")
    public String deleteFilm(@PathVariable("idActor") Integer idActor, @PathVariable("idFilm") Integer idFilm){
        actorService.removeFilm(idActor, idFilm);
        return "redirect:/actors/list";
    }

}
