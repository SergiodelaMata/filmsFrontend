package com.practices.sergiodelamata.filmsFrontend.controller;

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
        model.addAttribute("listCritics", list);
        model.addAttribute("page", pageRender);
        return "critics";
    }

    @GetMapping("/{idCritic}")
    public String searchCriticById(Model model, @PathVariable("idCritic") Integer idCritic,
                                 @RequestParam(name="mode", defaultValue = "request") String mode)
    {
        Critic critic = criticService.searchCriticById(idCritic);

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
        return "users/formUser";
    }

    @GetMapping("/title")
    public String searchUserByUsername(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                       @RequestParam("title") String title,
                                       @RequestParam(name="typeSearch", defaultValue = "nameFilm") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Critic> list;
        if(title.equals(""))
        {
            list = criticService.searchAll(pageable);
        }
        else
        {
            //Primero se buscan el listado de películas que contengan el título indicado y luego se cogen sus identificadores
            //para obtener la lista página de los comentarios de cada una de esas películas
            List<Film> listFilms = filmService.searchFilmsByTitle(title);
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
        model.addAttribute("listCritics", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "users/listUsers";
    }

    @PostMapping("/save")
    public String saveCritic(Model model, @RequestBody Critic critic, RedirectAttributes attributes)
    {
        criticService.saveCritic(critic);
        model.addAttribute("title", "Nueva crítica");
        attributes.addFlashAttribute("msg", "Los datos de la crítica se han guardado correctamente.");
        return "critics";
    }

    @PutMapping("/save")
    public String updateCritic(Model model, @RequestBody Critic critic, RedirectAttributes attributes)
    {
        criticService.saveCritic(critic);

        model.addAttribute("title", "Crítica actualizado");
        attributes.addFlashAttribute("msg", "Los datos de la crítica se han guardado correctamente.");
        return "users";
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
