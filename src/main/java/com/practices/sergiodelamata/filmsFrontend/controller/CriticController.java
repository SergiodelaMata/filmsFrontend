package com.practices.sergiodelamata.filmsFrontend.controller;

import com.practices.sergiodelamata.filmsFrontend.model.Actor;
import com.practices.sergiodelamata.filmsFrontend.model.Critic;
import com.practices.sergiodelamata.filmsFrontend.model.Film;
import com.practices.sergiodelamata.filmsFrontend.model.User;
import com.practices.sergiodelamata.filmsFrontend.paginator.PageRender;
import com.practices.sergiodelamata.filmsFrontend.service.ICriticService;
import com.practices.sergiodelamata.filmsFrontend.service.IFilmService;
import com.practices.sergiodelamata.filmsFrontend.service.IRoleService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/critics")
public class CriticController {
    @Autowired
    ICriticService criticService;

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IFilmService filmService;

    @GetMapping(value = {"/", ""})
    public String home(Model model, @RequestParam(name="page", defaultValue = "0") int page)
    {
        Pageable pageable = PageRequest.of(page, 5);
        List<Film> listFilms = filmService.searchAll();
        Page<Critic> list = criticService.searchAll(pageable);
        PageRender<Critic> pageRender = new PageRender<Critic>("/critics", list);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Listado de críticas");
        model.addAttribute("film", new Film());
        model.addAttribute("listFilms", listFilms);
        model.addAttribute("listCritics", list);
        model.addAttribute("page", pageRender);
        return "critics";
    }

    @GetMapping("/new")
    public String newCritic(Model model)
    {
        Critic critic = new Critic();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
            List<Film> listFilms = filmService.searchFilmsNotWithCriticFromUser(userLogged);
            model.addAttribute("listFilms", listFilms);
        }
        model.addAttribute("title", "FilmingApp | Nueva crítica");
        model.addAttribute("critic", critic);
        return "critics/formCriticNew";
    }

    @GetMapping("/new/{idFilm}")
    public String newCriticByFilm(Model model, @PathVariable("idFilm") Integer idFilm)
    {
        Critic critic = new Critic();
        Film film = filmService.searchFilmById(idFilm);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
            List<Film> listFilms = filmService.searchFilmsNotWithCriticFromUser(userLogged);
            model.addAttribute("listFilms", listFilms);
        }
        model.addAttribute("title", "FilmingApp | Nueva crítica");
        model.addAttribute("critic", critic);
        model.addAttribute("film", film);
        return "critics/formCriticNewFilm";
    }

    @GetMapping("/{idCritic}")
    public String searchCriticById(Model model, @PathVariable("idCritic") Integer idCritic,
                                 @RequestParam(name="mode", defaultValue = "request") String mode)
    {
        Critic critic = criticService.searchCriticById(idCritic);
        Film film = filmService.searchFilmById(critic.getIdFilm());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Consultar datos de la crítica");
        model.addAttribute("mode", mode);
        model.addAttribute("header", "Consultar datos de crítica");
        model.addAttribute("critic", critic);
        model.addAttribute("film", film);
        return "critics/formCritic";
    }

    @GetMapping("/edit/{idCritic}")
    public String editActor(Model model, @PathVariable("idCritic") Integer idCritic,
                            @RequestParam(name="mode", defaultValue = "edit") String mode)
    {
        Critic critic = criticService.searchCriticById(idCritic);
        Film film = filmService.searchFilmById(critic.getIdFilm());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Editar datos de la crítica");
        model.addAttribute("mode", mode);
        model.addAttribute("header", "Editar datos de crítica");
        model.addAttribute("critic", critic);
        model.addAttribute("film", film);
        return "critics/formCritic";
    }

    @GetMapping("/ownCritics")
    public String searchCriticOwnUser(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                       @RequestParam(name="typeSearch", defaultValue = "ownCritics") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Critic> list;
        List<Film> listFilms = filmService.searchAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
            List<Critic> listCriticsAux = userLogged.getCritics();
            List<Critic> listCritics = new ArrayList<>();
            //Se buscan los datos del usuario que ha realizado la crítica ya que se encuentra vacío
            for(int i = 0; i < listCriticsAux.size(); i++)
            {
                Critic criticAux = criticService.searchCriticById(listCriticsAux.get(i).getIdCritic());
                listCritics.add(criticAux);
            }
            Critic critic = new Critic();
            list = critic.getPageCritic(listCritics, pageable);
            PageRender<Critic> pageRender = new PageRender<Critic>("/critics/ownCritics?typeSearch=" + typeSearch, list);
            model.addAttribute("listCritics", list);
            model.addAttribute("page", pageRender);
        }

        model.addAttribute("title", "FilmingApp | Listado de críticas por críticas del usuario");
        model.addAttribute("film", new Film());
        model.addAttribute("listFilms", listFilms);
        model.addAttribute("typeSearch", typeSearch);
        return "critics/listCritics";
    }

    @GetMapping("/title")
    public String searchCriticByTitleFilm(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                       @RequestParam("title") String title,
                                       @RequestParam(name="typeSearch", defaultValue = "title") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Critic> list;
        List<Film> listFilms;
        if(title.equals(""))
        {
            list = criticService.searchAll(pageable);
            listFilms = filmService.searchAll();
        }
        else
        {
            //Primero se buscan el listado de películas que contengan el título indicado y luego se cogen sus identificadores
            //para obtener la lista página de los comentarios de cada una de esas películas
            listFilms = filmService.searchFilmsByTitle(title);
            list = criticService.searchCriticByIdFilms(listFilms, pageable);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        PageRender<Critic> pageRender = new PageRender<Critic>("/critics/title?title=" + title + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de críticas por títulos de película");
        model.addAttribute("film", new Film());
        model.addAttribute("listFilms", listFilms);
        model.addAttribute("listCritics", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "critics/listCritics";
    }

    @PostMapping("/save")
    public String saveCritic(Model model, @RequestBody Critic critic, RedirectAttributes attributes)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            critic.setUser(userLogged);
        }
        critic.setDateCritic(new Date());
        criticService.saveCritic(critic);
        model.addAttribute("title", "Nueva crítica");
        attributes.addFlashAttribute("msg", "Los datos de la crítica se han guardado correctamente.");
        return "critics";
    }

    @PostMapping("/save/{idFilm}")
    public String saveCritic(Model model, @PathVariable("idFilm") Integer idFilm, @RequestBody Critic critic, RedirectAttributes attributes)
    {
        critic.setIdFilm(idFilm);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            critic.setUser(userLogged);
        }
        critic.setDateCritic(new Date());
        criticService.saveCritic(critic);
        model.addAttribute("title", "Nueva crítica");
        attributes.addFlashAttribute("msg", "Los datos de la crítica se han guardado correctamente.");
        return "critics";
    }

    @PutMapping("/save")
    public String updateCritic(Model model, @RequestBody Critic critic, RedirectAttributes attributes)
    {
        Critic criticAux = criticService.searchCriticById(critic.getIdCritic());
        critic.setDateCritic(criticAux.getDateCritic());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            critic.setUser(userLogged);
            criticService.saveCritic(critic);
        }

        model.addAttribute("title", "Crítica actualizado");
        attributes.addFlashAttribute("msg", "Los datos de la crítica se han guardado correctamente.");
        return "critics";
    }

    @DeleteMapping("/delete/{idCritic}")
    public String deleteCritic(Model model, @PathVariable("idCritic") Integer idCritic, RedirectAttributes attributes) {
        Critic critic = criticService.searchCriticById(idCritic);
        if (critic != null) {
            criticService.deleteCritic(idCritic);
            attributes.addFlashAttribute("msg", "Los datos de la crítica fueron borrados");
        } else {
            attributes.addFlashAttribute("msg", "La crítica no ha sido encontrado");
        }

        return "redirect:/critics";
    }
}
