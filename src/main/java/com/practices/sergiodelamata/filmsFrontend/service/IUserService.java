package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.User;
import com.practices.sergiodelamata.filmsFrontend.model.UserLogin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<User> searchAll(Pageable pageable);

    User searchUserById(Integer idUser);

    User searchUserByUsername(String username);

    User searchUserByEmail(String email);

    User login(String email, String password);

    UserLogin loginSecurity(String email, String password);

    void saveUser(User user);

    void deleteUser(Integer idUser);

}
