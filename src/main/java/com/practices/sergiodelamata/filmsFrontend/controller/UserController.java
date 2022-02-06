package com.practices.sergiodelamata.filmsFrontend.controller;

import com.practices.sergiodelamata.filmsFrontend.config.CustomAuthenticationProvider;
import com.practices.sergiodelamata.filmsFrontend.model.*;
import com.practices.sergiodelamata.filmsFrontend.paginator.PageRender;
import com.practices.sergiodelamata.filmsFrontend.service.IFilmService;
import com.practices.sergiodelamata.filmsFrontend.service.IRoleService;
import com.practices.sergiodelamata.filmsFrontend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
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
        Page<User> list = userService.searchAll(pageable);
        PageRender<User> pageRender = new PageRender<User>("/users", list);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Listado de usuarios");
        model.addAttribute("listUsers", list);
        model.addAttribute("page", pageRender);
        return "users";
    }

    @GetMapping("/{idUser}")
    public String searchFilmById(Model model, @PathVariable("idUser") Integer idUser,
                                 @RequestParam(name="mode", defaultValue = "request") String mode)
    {
        User user = userService.searchUserById(idUser);
        List<Critic> listCritics = user.getCritics();
        List<Film> listFilms = new ArrayList<>();
        for(int i = 0; i < listCritics.size(); i++)
        {
            Film film = filmService.searchFilmById(listCritics.get(i).getIdFilm());
            listFilms.add(film);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Consultar datos del usuario");
        model.addAttribute("mode", mode);
        model.addAttribute("header", "Consultar datos de usuario");
        model.addAttribute("user", user);
        model.addAttribute("films", listFilms);
        return "users/formUser";
    }

    @GetMapping("/username")
    public String searchUserByUsername(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                    @RequestParam("username") String username,
                                    @RequestParam(name="typeSearch", defaultValue = "username") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<User> list;
        if(username.equals(""))
        {
            list = userService.searchAll(pageable);
        }
        else
        {
            list = userService.searchUserByUsername(username, pageable);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        PageRender<User> pageRender = new PageRender<User>("/users/username?username=" + username + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de usuarios por nombre de usuario");
        model.addAttribute("listUsers", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "users/listUsers";
    }

    @GetMapping("/email")
    public String searchUserByEmail(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                    @RequestParam("email") String email,
                                    @RequestParam(name="typeSearch", defaultValue = "email") String typeSearch)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<User> list;
        if(email.equals(""))
        {
            list = userService.searchAll(pageable);
        }
        else
        {
            list = userService.searchUserByEmail(email, pageable);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        PageRender<User> pageRender = new PageRender<User>("/users/email?email=" + email + "&typeSearch=" + typeSearch, list);
        model.addAttribute("title", "FilmingApp | Listado de usuarios por nombre de email");
        model.addAttribute("listUsers", list);
        model.addAttribute("page", pageRender);
        model.addAttribute("typeSearch", typeSearch);
        return "users/listUsers";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Añadir un nuevo usuario");
        User user = new User();
        model.addAttribute("user", user);
        return "users/formUserNew";
    }

    @GetMapping("/registry")
    public String registry(Model model){
        model.addAttribute("title", "FilmingApp | Registrar usuario");
        User user = new User();
        model.addAttribute("user", user);
        return "users/formUserRegistry";
    }

    @PostMapping("/save")
    public String saveUser(Model model, @RequestBody User user, RedirectAttributes attributes)
    {
        user.setEnable(0);
        userService.saveUser(user);
        model.addAttribute("title", "Nuevo usuario");
        attributes.addFlashAttribute("msg", "Los datos del usuario se han guardado correctamente.");
        return "users";
    }

    @PutMapping("/save")
    public String updateUser(Model model, @RequestBody User user, RedirectAttributes attributes)
    {
        User userAux = userService.searchUserById(user.getIdUser());
        user.setEnable(userAux.getEnable());
        user.setRoles(userAux.getRoles());
        userService.saveUser(user);

        model.addAttribute("title", "Usuario actualizado");
        attributes.addFlashAttribute("msg", "Los datos del usuario se han guardado correctamente.");
        return "users";
    }

    @PutMapping("/acceptRequest/{idUser}")
    public String acceptRequest(Model model, @PathVariable("idUser") Integer idUser, RedirectAttributes attributes)
    {
        User user = userService.searchUserById(idUser);
        List<Role> listRoles = roleService.searchAll();
        //Se limpia la lista de roles que pudiera tener el usuario y se añade el rol nuevo que va a tener el usuario
        user.setEnable(1);
        user.setRoles(new ArrayList<>());
        user.getRoles().add(listRoles.get(1));
        userService.saveUser(user);
        model.addAttribute("title", "Incrementado el rol del usuario");
        attributes.addFlashAttribute("msg", "Se ha establecido un rol al usuario.");
        return "users";
    }

    @PutMapping("/increaseRol/{idUser}")
    public String increaseRol(Model model, @PathVariable("idUser") Integer idUser, RedirectAttributes attributes)
    {
        User user = userService.searchUserById(idUser);
        List<Role> listRoles = roleService.searchAll();
        //Se limpia la lista de roles que pudiera tener el usuario y se añade el rol nuevo que va a tener el usuario
        user.setRoles(new ArrayList<>());
        user.getRoles().add(listRoles.get(0));
        userService.saveUser(user);
        model.addAttribute("title", "Incrementado el rol del usuario");
        attributes.addFlashAttribute("msg", "El rol del usuario se ha actualizado.");
        return "users";
    }

    @PutMapping("/decreaseRol/{idUser}")
    public String decreaseRol(Model model, @PathVariable("idUser") Integer idUser, RedirectAttributes attributes)
    {
        User user = userService.searchUserById(idUser);
        List<Role> listRoles = roleService.searchAll();
        //Se limpia la lista de roles que pudiera tener el usuario y se añade el rol nuevo que va a tener el usuario
        user.setRoles(new ArrayList<>());
        user.getRoles().add(listRoles.get(1));
        userService.saveUser(user);
        model.addAttribute("title", "Reducido el rol del usuario");
        attributes.addFlashAttribute("msg", "El rol del usuario se ha actualizado.");
        return "users";
    }


    @PostMapping("/registry")
    public String saveRegistry(Model model, @RequestBody User user, RedirectAttributes attributes)
    {
        user.setEnable(0);
        userService.saveUser(user);
        model.addAttribute("title", "Registrar usuario");
        attributes.addFlashAttribute("msg", "Los datos del registro fueron guardados");
        return "redirect:/login";
    }

    @GetMapping("/edit/{idUser}")
    public String editUser(Model model, @PathVariable("idUser") Integer idUser,
                                @RequestParam(name="mode", defaultValue = "edit") String mode) {
        User user = userService.searchUserById(idUser);
        List<Critic> listCritics = user.getCritics();
        List<Film> listFilms = new ArrayList<>();
        for(int i = 0; i < listCritics.size(); i++)
        {
            Film film = filmService.searchFilmById(listCritics.get(i).getIdFilm());
            listFilms.add(film);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Editar datos del usuario");
        model.addAttribute("mode", mode);
        model.addAttribute("user", user);
        model.addAttribute("films", listFilms);
        return "users/formUser";
    }

    @GetMapping("/profile/{idUser}")
    public String editProfile(Model model, @PathVariable("idUser") Integer idUser) {
        User user = userService.searchUserById(idUser);
        List<Critic> listCritics = user.getCritics();
        List<Film> listFilms = new ArrayList<>();
        for(int i = 0; i < listCritics.size(); i++)
        {
            Film film = filmService.searchFilmById(listCritics.get(i).getIdFilm());
            listFilms.add(film);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        model.addAttribute("title", "FilmingApp | Editar datos del usuario");
        model.addAttribute("user", user);
        model.addAttribute("films", listFilms);
        return "users/formUserProfile";
    }


    @DeleteMapping("/delete/{idUser}")
    public String eliminarUsuario(Model model, @PathVariable("idUser") Integer idUser, RedirectAttributes attributes) {
        User user = userService.searchUserById(idUser);
        if (user != null) {
            userService.deleteUser(idUser);
            attributes.addFlashAttribute("msg", "Los datos del usuario fueron borrados");
        } else {
            attributes.addFlashAttribute("msg", "El usuario no ha sido encontrado");
        }

        return "redirect:/users";
    }
}
