package com.practices.sergiodelamata.filmsFrontend.controller;

import com.practices.sergiodelamata.filmsFrontend.model.Role;
import com.practices.sergiodelamata.filmsFrontend.model.User;
import com.practices.sergiodelamata.filmsFrontend.paginator.PageRender;
import com.practices.sergiodelamata.filmsFrontend.service.IRoleService;
import com.practices.sergiodelamata.filmsFrontend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping(value = {"/", ""})
    public String home(Model model, @RequestParam(name="page", defaultValue = "0") int page)
    {
        Pageable pageable = PageRequest.of(page, 5);
        Page<User> list = userService.searchAll(pageable);
        PageRender<User> pageRender = new PageRender<User>("/users", list);

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
        model.addAttribute("title", "FilmingApp | Consultar datos del usuario");
        model.addAttribute("mode", mode);
        model.addAttribute("header", "Consultar datos de usuario");
        model.addAttribute("user", user);
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
            List<User> listUsers = new ArrayList<>();
            listUsers.add(userService.searchUserByUsername(username));
            list = new PageImpl<>(listUsers, PageRequest.of(0, 5), listUsers.size());
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
            List<User> listUsers = new ArrayList<>();
            listUsers.add(userService.searchUserByEmail(email));
            list = new PageImpl<>(listUsers, PageRequest.of(0, 5), listUsers.size());
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
        List<Role> roles = roleService.searchAll();
        model.addAttribute("title", "FilmingApp | Añadir un nuevo usuario");
        model.addAttribute("allRoles", roles);
        User user = new User();
        model.addAttribute("user", user);
        return "users/formUserNew";
    }

    @GetMapping("/registry")
    public String registry(Model model){
        model.addAttribute("title", "FilmingApp | Añadir un nuevo usuario");
        User user = new User();
        model.addAttribute("user", user);
        return "users/formUserRegistry";
    }

    @PostMapping("/save")
    public String saveUser(Model model, @RequestBody User user, RedirectAttributes attributes)
    {
        userService.saveUser(user);
        model.addAttribute("title", "Nuevo usuario");
        attributes.addFlashAttribute("msg", "Los datos del usuario se han guardado correctamente.");
        return "users";
    }

    @PutMapping("/save")
    public String updateUser(Model model, @RequestBody User user, RedirectAttributes attributes)
    {
        User userAux = userService.searchUserById(user.getIdUser());
        user.setCritics(userAux.getCritics());
        user.setRoles(userAux.getRoles());
        userService.saveUser(user);
        model.addAttribute("title", "Usuario actualizado");
        attributes.addFlashAttribute("msg", "Los datos del usuario se han guardado correctamente.");
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
    public String saveRegistry(Model model, User user, RedirectAttributes attributes)
    {
        user.setEnable(false);
        //Role role = roleService.searchRoleById(2); // User role
        //user.setRoles(Arrays.asList(role));
        userService.saveUser(user);
        attributes.addFlashAttribute("msg", "Los datos del registro fueron guardados");
        return "redirect:/login";
    }

    @GetMapping("/edit/{idUser}")
    public String editarUsuario(Model model, @PathVariable("idUser") Integer idUser) {
        User user = userService.searchUserById(idUser);
        model.addAttribute("title", "Editar usuario");
        model.addAttribute("user", user);
        List<Role> roles = roleService.searchAll();
        model.addAttribute("allRoles", roles);
        return "users/formUser";
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
