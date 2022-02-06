package com.practices.sergiodelamata.filmsFrontend.config;

import com.practices.sergiodelamata.filmsFrontend.model.Role;
import com.practices.sergiodelamata.filmsFrontend.model.UserLogin;
import com.practices.sergiodelamata.filmsFrontend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private IUserService userService;
    public CustomAuthenticationProvider() {
        super();
    }
    @Override
    public Authentication authenticate(final Authentication authentication) throws
            AuthenticationException {
        final String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserLogin userLogged = userService.loginSecurity(email, password);
        if (userLogged != null) {
            final List<GrantedAuthority> grantedAuths = new
                    ArrayList<GrantedAuthority>();
            for (Role rol : userLogged.getRoles()) {
                grantedAuths.add(new SimpleGrantedAuthority(rol.getAuthority()));
            }
            final UserDetails principal = new User(email, password, grantedAuths);
            final Authentication auth = new
                    UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
            return auth;
        }
        return null;
    }
    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(final Class authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}