package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Role;

import java.util.List;

public interface IRoleService {
    List<Role> searchAll();

    Role searchRoleById(Integer idRole);

    void saveRole(Role role);

    void deleteRole(Integer idRole);
}
