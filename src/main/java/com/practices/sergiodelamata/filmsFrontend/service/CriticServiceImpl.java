package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Critic;
import com.practices.sergiodelamata.filmsFrontend.model.Film;
import com.practices.sergiodelamata.filmsFrontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CriticServiceImpl implements ICriticService{
    @Autowired
    RestTemplate template;

    @Autowired
    IUserService userService;

    @Autowired
    IFilmService filmService;


    String url = "http://localhost:8090/api/users-critics/critics";

    @Override
    public Page<Critic> searchAll(Pageable pageable) {
        Critic critic = new Critic();
        Critic[] critics = template.getForObject(url, Critic[].class);
        List<Critic> listCritics = Arrays.asList(critics);

        Page<Critic> page = critic.getPageCritic(listCritics, pageable);
        return page;
    }

    @Override
    public Critic searchCriticById(Integer idCritic) {
        Critic critic = template.getForObject(url + "/" + idCritic, Critic.class);
        return critic;
    }

    @Override
    public Page<Critic> searchCriticByIdFilm(Integer idFilm, Pageable pageable) {
        Critic critic = new Critic();
        Critic[] critics = template.getForObject(url + "/idFilm/" + idFilm, Critic[].class);
        List<Critic> listCritics = Arrays.asList(critics);

        Page<Critic> page = critic.getPageCritic(listCritics, pageable);
        return page;
    }

    @Override
    public void saveCritic(Critic critic) {
        if (critic.getIdCritic() != null && critic.getIdCritic() > 0) {
            template.put(url, critic);
        } else {
            User user = userService.searchUserById(critic.getIdUser().getIdUser());
            Film film = filmService.searchFilmById(critic.getIdFilm());
            //Se comprueba si existe el usuario
            if(user != null) {
                //Se comprueba si existe la película
                if (film != null) {
                    boolean verify = false;
                    int position = 0;
                    List<Critic> listCritics = user.getCritics();
                    //Se comprueba si el usuario ya realizó una crítica a dicha película o no entre el listado de sus comentarios
                    for (int i = 0; i < listCritics.size(); i++) {
                        //Se comprueba si el usuario ya realizó una crítica a dicha película o no
                        if (Objects.equals(listCritics.get(i).getIdUser().getIdUser(), critic.getIdUser().getIdUser())) {
                            verify = true;
                            position = i;
                        }
                    }
                    //En el caso de que no se haya encontrado ningún comentario del usuario para esa película, se guarda el comentario
                    if (!verify) {
                        //Insertamos un nuevo comentario
                        template.postForObject(url, critic, String.class);
                    //En caso contrario, se actualiza el comentario
                    } else {
                        //Actualizamos el comentario volviéndolo a poner su identificador antes de guardar los cambios
                        critic.setIdCritic(listCritics.get(position).getIdCritic());
                        template.put(url, critic);
                    }
                }
            }
        }
    }

    @Override
    public void deleteCritic(Integer idCritic) {
        template.delete(url + "/" + idCritic);
    }
}
