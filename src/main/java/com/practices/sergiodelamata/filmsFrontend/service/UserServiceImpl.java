package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.User;
import com.practices.sergiodelamata.filmsFrontend.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    RestTemplate template;

    String url = "http://localhost:8090/api/users-critics/users";

    @Override
    public Page<User> searchAll(Pageable pageable) {
        User[] users = template.getForObject(url, User[].class);
        List<User> listUsers = Arrays.asList(users);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> list;
        if(listUsers.size() < startItem)
        {
            list = Collections.emptyList();
        }
        else
        {
            int toIndex = Math.min(startItem + pageSize, listUsers.size());
            list = listUsers.subList(startItem, toIndex);
        }

        Page<User> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), listUsers.size());
        return page;
    }

    @Override
    public User searchUserById(Integer idUser) {
        User user = template.getForObject(url + "/" + idUser, User.class);
        return user;
    }

    @Override
    public User searchUserByUsername(String username) {
        User user = template.getForObject(url + "/username/" + username, User.class);
        return user;
    }

    @Override
    public User searchUserByEmail(String email) {
        User user = template.getForObject(url + "/email/" + email, User.class);
        return user;
    }

    @Override
    public User login(String email, String password) {
        User user = template.getForObject(url + "/login/" + email + "/" + password, User.class);
        return user;
    }

    @Override
    public UserLogin loginSecurity(String email, String password) {
        User user = login(email, password);
        return new UserLogin(user);
    }

    @Override
    public void saveUser(User user) {
        if(user.getIdUser() != null && user.getIdUser() > 0){
            template.put(url, user);
        }
        else
        {
            user.setIdUser(0);
            template.postForObject(url, user, String.class);
        }
    }

    @Override
    public void deleteUser(Integer idUser) {
        template.delete(url + "/" + idUser);
    }
}
