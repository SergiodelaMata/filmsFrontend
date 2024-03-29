package com.practices.sergiodelamata.filmsFrontend.controller;

import com.practices.sergiodelamata.filmsFrontend.model.Actor;
import com.practices.sergiodelamata.filmsFrontend.model.Critic;
import com.practices.sergiodelamata.filmsFrontend.model.Film;
import com.practices.sergiodelamata.filmsFrontend.model.User;
import com.practices.sergiodelamata.filmsFrontend.paginator.PageRender;
import com.practices.sergiodelamata.filmsFrontend.service.IActorService;
import com.practices.sergiodelamata.filmsFrontend.service.ICriticService;
import com.practices.sergiodelamata.filmsFrontend.service.IFilmService;
import com.practices.sergiodelamata.filmsFrontend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/films")
public class FilmController {
    @Autowired
    IFilmService filmService;

    @Autowired
    IActorService actorService;

    @Autowired
    IUserService userService;

    @Autowired
    ICriticService criticService;

    @GetMapping(value = {"/", ""})
    public String home(Model model, @RequestParam(name="page", defaultValue = "0") int page)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list = filmService.searchAll(pageable);
        PageRender<Film> pageRender = new PageRender<Film>("/films", list);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Listado de películas");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        return "films";
    }

    @GetMapping("/new")
    public String newFilm(Model model)
    {
        Film film = new Film();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Nueva película");
        model.addAttribute("film", film);
        return "films/formFilmNew";
    }

    @GetMapping("/{idFilm}")
    public String searchFilmById(Model model, @PathVariable("idFilm") Integer idFilm,
                                 @RequestParam(name="mode", defaultValue = "request") String mode)
    {
        Film film = filmService.searchFilmById(idFilm);
        List<Critic> listCritics = criticService.searchCriticByIdFilm(idFilm);

        double avgMark = 0;
        //Se obtiene la media de las notas puestas por los usuarios
        for(int i = 0; i < listCritics.size(); i++)
        {
            avgMark += listCritics.get(i).getMark();
        }
        avgMark /= listCritics.size();
        avgMark = (double) Math.round(avgMark * 100) / 100;
        model.addAttribute("averageMark", avgMark);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Consultar datos de película");
        model.addAttribute("mode", mode);
        model.addAttribute("header", "Consultar datos de película");
        model.addAttribute("film", film);
        model.addAttribute("listCritics", listCritics);
        return "films/formFilm";
    }

    @GetMapping("/title")
    public String searchFilmByTitle(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                    @RequestParam("title") String title,
                                    @RequestParam(name="typeSearch", defaultValue = "title") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list;
        if(title.equals(""))
        {
            list = filmService.searchAll(pageable);
        }
        else
        {
            list = filmService.searchFilmsByTitle(title, pageable);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        PageRender<Film> pageRender = new PageRender<Film>("/films/title?title=" + title + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de películas por título");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "films/listFilms";
    }

    @GetMapping("/year")
    public String searchFilmByYear(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                   @RequestParam("yearInit") Integer yearInit, @RequestParam("yearEnd") Integer yearEnd,
                                   @RequestParam(name="typeSearch", defaultValue = "year") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list = filmService.searchFilmsByYear(yearInit, yearEnd, pageable);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        PageRender<Film> pageRender = new PageRender<Film>("/films/year?yearInit=" + yearInit + "&yearEnd=" + yearEnd + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de películas por año");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "films/listFilms";
    }

    @GetMapping("/country")
    public String searchFilmByCountry(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                      @RequestParam("country") String country,
                                      @RequestParam(name="typeSearch", defaultValue = "country") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list;
        if(country.equals(""))
        {
            list = filmService.searchAll(pageable);
        }
        else
        {
            list = filmService.searchFilmsByCountry(country, pageable);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        PageRender<Film> pageRender = new PageRender<Film>("/films/country?country=" + country + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de películas por país");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "films/listFilms";
    }

    @GetMapping("/direction")
    public String searchFilmByDirection(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                        @RequestParam("direction") String direction,
                                        @RequestParam(name="typeSearch", defaultValue = "direction") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list;
        if(direction.equals(""))
        {
            list = filmService.searchAll(pageable);
        }
        else
        {
            list = filmService.searchFilmsByDirection(direction, pageable);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        PageRender<Film> pageRender = new PageRender<Film>("/films/direction?direction=" + direction + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de películas por su dirección");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "films/listFilms";
    }

    @GetMapping("/genres")
    public String searchFilmByGenres(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                     @RequestParam("genres") String genres,
                                     @RequestParam(name="typeSearch", defaultValue = "genres") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list;
        if(genres.equals(""))
        {
            list = filmService.searchAll(pageable);
        }
        else
        {
            list = filmService.searchFilmsByGenres(genres, pageable);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        PageRender<Film> pageRender = new PageRender<Film>("/films/genres?genres=" + genres + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de películas por su género");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "films/listFilms";
    }

    @GetMapping("/actorName")
    public String searchFilmsByActorsName(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                          @RequestParam("actorName") String actorName,
                                          @RequestParam(name="typeSearch", defaultValue = "actorName") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list;
        if(actorName.equals(""))
        {
            list = filmService.searchAll(pageable);
        }
        else
        {
            list = actorService.searchFilmsByActorsName(actorName, pageable);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        PageRender<Film> pageRender = new PageRender<Film>("/films/actorName?actorName=" + actorName + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de películas por nombre de actor");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "films/listFilms";
    }

    @GetMapping("/actor/{idFilm}")
    public String insertActorToFilm(Model model, @PathVariable("idFilm") Integer idFilm){
        Film film = filmService.searchFilmById(idFilm);
        List<Actor> listActorNotInFilm = actorService.searchActorsNotInFilm(film);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Nuevo actor para el reparto");
        model.addAttribute("idFilm", idFilm);
        model.addAttribute("listActors", listActorNotInFilm);

        return "films/formAddActorFilm";
    }

    @PostMapping("/save")
    public String saveFilm(Model model, @RequestBody Film film, RedirectAttributes attributes)
    {
        filmService.saveFilm(film);
        model.addAttribute("title", "Nueva película");
        attributes.addFlashAttribute("msg", "Los datos de la película se han guardado correctamente.");
        return "films";
    }

    @PutMapping ("/save")
    public String updateFilm(Model model, @RequestBody Film film, RedirectAttributes attributes)
    {
        Film filmAux = filmService.searchFilmById(film.getIdFilm());
        film.setActors(filmAux.getActors());
        filmService.saveFilm(film);
        model.addAttribute("title", "Película actualizada");
        attributes.addFlashAttribute("msg", "Los datos de la película se han guardado correctamente.");
        return "films";
    }

    @GetMapping("/edit/{idFilm}")
    public String editFilm(Model model, @PathVariable("idFilm") Integer idFilm, @RequestParam(name="mode", defaultValue = "edit") String mode)
    {
        Film film = filmService.searchFilmById(idFilm);
        List<Critic> listCritics = criticService.searchCriticByIdFilm(idFilm);

        double avgMark = 0;
        //Se obtiene la media de las notas puestas por los usuarios
        for(int i = 0; i < listCritics.size(); i++)
        {
            avgMark += listCritics.get(i).getMark();
        }
        avgMark /= listCritics.size();
        avgMark = (double) Math.round(avgMark * 100) / 100;
        model.addAttribute("averageMark", avgMark);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Editar datos de película");
        model.addAttribute("mode", mode);
        model.addAttribute("header", "Editar datos de película");
        model.addAttribute("film", film);
        model.addAttribute("listCritics", listCritics);
        return "films/formFilm";
    }

    @DeleteMapping("/delete/{idFilm}")
    public String deleteFilm(Model model, @PathVariable("idFilm") Integer idFilm, RedirectAttributes attributes)
    {
        //Se eliminan primero las distintas críticas que pueda tener la película eliminada
        List<Critic> criticList = criticService.searchCriticByIdFilm(idFilm);
        for(int i = 0; i < criticList.size(); i++)
        {
            criticService.deleteCritic(criticList.get(i).getIdCritic());
        }
        filmService.deleteFilm(idFilm);
        attributes.addFlashAttribute("msg", "Se ha borrado la película correctamente.");
        return "films";
    }

    @PutMapping("/insert/actor/{idFilm}/{idActor}")
    public String insertActors(@PathVariable("idFilm") Integer idFilm, @PathVariable("idActor") Integer idActor){
        filmService.insertActor(idFilm, idActor);
        return "films/formFilm";
    }

    @DeleteMapping("/delete/actor/{idFilm}/{idActor}")
    public String deleteFilm(@PathVariable("idFilm") Integer idFilm, @PathVariable("idActor") Integer idActor){
        filmService.removeActor(idFilm, idActor);
        return "films/formFilm";
    }
}
