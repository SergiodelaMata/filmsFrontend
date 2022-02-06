package com.practices.sergiodelamata.filmsFrontend.controller;

import com.practices.sergiodelamata.filmsFrontend.model.User;
import com.practices.sergiodelamata.filmsFrontend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    IUserService userService;

    @GetMapping (value = {"/", "/home", ""})
    public String home(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentEmail = authentication.getName();
            model.addAttribute("email", currentEmail);
            User userLogged = userService.searchUserByEmailUnique(currentEmail);
            model.addAttribute("userLogged", userLogged);
        }
        return "home";
    }
}
