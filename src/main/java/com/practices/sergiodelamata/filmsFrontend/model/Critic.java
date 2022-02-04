package com.practices.sergiodelamata.filmsFrontend.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Critic {
    private Integer idCritic;
    private Integer idFilm;
    private User idUser;
    private String valoration;
    private Integer mark;
    private Date dateCritic;

    public Critic() {}

    public Critic(Integer idCritic, Integer idFilm, User idUser, String valoration, Integer mark, Date dateCritic) {
        this.idCritic = idCritic;
        this.idFilm = idFilm;
        this.idUser = idUser;
        this.valoration = valoration;
        this.mark = mark;
        this.dateCritic = dateCritic;
    }

    public Date getDateCritic() {
        return dateCritic;
    }

    public void setDateCritic(Date dateCritic) {
        this.dateCritic = dateCritic;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getValoration() {
        return valoration;
    }

    public void setValoration(String valoration) {
        this.valoration = valoration;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Integer getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Integer idFilm) {
        this.idFilm = idFilm;
    }

    public Integer getIdCritic() {
        return idCritic;
    }

    public void setIdCritic(Integer idCritic) {
        this.idCritic = idCritic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        Critic critic = (Critic) o;
        return Objects.equals(idCritic, critic.idCritic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCritic);
    }

    public Page<Critic> getPageCritic(List<Critic> listCritics, Pageable pageable){
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Critic> list;
        if(listCritics.size() < startItem)
        {
            list = Collections.emptyList();
        }
        else
        {
            int toIndex = Math.min(startItem + pageSize, listCritics.size());
            list = listCritics.subList(startItem, toIndex);
        }
        Page<Critic> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), listCritics.size());
        return page;
    }


}
