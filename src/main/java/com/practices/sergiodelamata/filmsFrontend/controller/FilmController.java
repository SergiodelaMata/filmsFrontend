package com.practices.sergiodelamata.filmsFrontend.controller;

import com.practices.sergiodelamata.filmsFrontend.model.Film;
import com.practices.sergiodelamata.filmsFrontend.paginator.PageRender;
import com.practices.sergiodelamata.filmsFrontend.service.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/films")
public class FilmController {
    @Autowired
    IFilmService filmService;

    @GetMapping(value = {"/", ""})
    public String home(Model model, @RequestParam(name="page", defaultValue = "0") int page)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list = filmService.searchAll(pageable);
        PageRender<Film> pageRender = new PageRender<Film>("/films/list", list);

        model.addAttribute("title", "Listado de películas");
        model.addAttribute("listFilms", list);
        return "films";
    }

    @GetMapping("/new")
    public String newFilm(Model model)
    {
        model.addAttribute("title", "Nuevo actor");
        Film film = new Film();
        model.addAttribute("film", film);
        return "films/formFilm";
    }

    @GetMapping("/search")
    public String searchFilm(Model model)
    {
        return "films/searchFilm";
    }

    @GetMapping("/list")
    public String listFilms(Model model, @RequestParam(name="page", defaultValue = "0") int page)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list = filmService.searchAll(pageable);
        PageRender<Film> pageRender = new PageRender<Film>("/films/list", list);

        model.addAttribute("title", "Listado de películas");
        model.addAttribute("listFilms", list);
        return "actors/listActors";
    }

    @GetMapping("/{idFilm}")
    public String searchFilmById(Model model, @PathVariable("idFilm") Integer idFilm)
    {
        Film film = filmService.searchFilmById(idFilm);
        model.addAttribute("film", film);
        return "films/formFilm";
    }

    @GetMapping("/title")
    public String searchFilmByTitle(Model model, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam("title") String title)
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
        PageRender<Film> pageRender = new PageRender<Film>("/list", list);
        model.addAttribute("title", "Listado de películas por título");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        return "films/listFilms";
    }

    @GetMapping("/year")
    public String searchFilmByYear(Model model, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam("yearInit") Integer yearInit, @RequestParam("yearEnd") Integer yearEnd)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list = filmService.searchFilmsByYear(yearInit, yearEnd, pageable);
        PageRender<Film> pageRender = new PageRender<Film>("/list", list);
        model.addAttribute("title", "Listado de películas por año");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        return "films/listFilms";
    }

    @GetMapping("/country")
    public String searchFilmByCountry(Model model, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam("country") String country)
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
        PageRender<Film> pageRender = new PageRender<Film>("/list", list);
        model.addAttribute("title", "Listado de películas por país");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        return "films/listFilms";
    }

    @GetMapping("/direction")
    public String searchFilmByDirection(Model model, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam("direction") String direction)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list;
        if(direction.equals(""))
        {
            list = filmService.searchAll(pageable);
        }
        else
        {
            list = filmService.searchFilmsByTitle(direction, pageable);
        }
        PageRender<Film> pageRender = new PageRender<Film>("/list", list);
        model.addAttribute("title", "Listado de películas por su dirección");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        return "films/listFilms";
    }

    @GetMapping("/genres")
    public String searchFilmByGenres(Model model, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam("genres") String genres)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Film> list;
        if(genres.equals(""))
        {
            list = filmService.searchAll(pageable);
        }
        else
        {
            list = filmService.searchFilmsByTitle(genres, pageable);
        }
        PageRender<Film> pageRender = new PageRender<Film>("/list", list);
        model.addAttribute("title", "Listado de películas por su género");
        model.addAttribute("listFilms", list);
        model.addAttribute("page", pageRender);
        return "films/listFilms";
    }

    @PostMapping("/save")
    public String saveFilm(Model model, Film film, RedirectAttributes attributes)
    {
        filmService.saveFilm(film);
        model.addAttribute("title", "Nueva película");
        attributes.addFlashAttribute("msg", "Los datos de la película se han guardado correctamente.");
        return "redirect:/films/list";
    }

    @GetMapping("/edit/{idFilm}")
    public String editFilm(Model model, @PathVariable("idFilm") Integer idFilm)
    {
        Film film = filmService.searchFilmById(idFilm);
        model.addAttribute("title", "Editar datos de la película");
        model.addAttribute("film", film);
        return "films/formFilms";
    }

    @GetMapping("/delete/{idFilm}")
    public String deleteActor(Model model, @PathVariable("idFilm") Integer idFilm, RedirectAttributes attributes)
    {
        filmService.deleteFilm(idFilm);
        attributes.addFlashAttribute("msg", "Se ha borrado la película correctamente.");
        return "redirect:/films/list";
    }

}
