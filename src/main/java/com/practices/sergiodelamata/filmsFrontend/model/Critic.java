package com.practices.sergiodelamata.filmsFrontend.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Critic {
    private Integer idCritic;
    private Integer idFilm;
    private User user;
    private String valoration;
    private Integer mark;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCritic;

    public Critic() {}

    public Critic(Integer idCritic, Integer idFilm, User user, String valoration, Integer mark, Date dateCritic) {
        this.idCritic = idCritic;
        this.idFilm = idFilm;
        this.user = user;
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

    public String getDateCriticFormat()
    {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(this.dateCritic);
        return date;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
