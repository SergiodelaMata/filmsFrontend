package com.practices.sergiodelamata.filmsFrontend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model, Principal principal) {
        model.addAttribute("title", "FilmingApp | Login");
        if (principal != null) {
            return "redirect:/home";
        }
        if (error != null) {
            model.addAttribute("msg",
                    "¡Error al iniciar sesión: Nombre de usuario o contraseña incorrecta, " +
                            "por favor vuelva a intentarlo!");
        }
        return "login";
    }
    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse
            response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}

