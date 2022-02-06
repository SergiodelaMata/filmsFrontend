package com.practices.sergiodelamata.filmsFrontend.service;

import com.practices.sergiodelamata.filmsFrontend.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService{
    @Autowired
    RestTemplate template;

    String url = "http://localhost:8090/api/users-critics/roles";

    @Override
    public List<Role> searchAll() {
        Role[] roles = template.getForObject(url, Role[].class);
        return Arrays.asList(roles);
    }

    @Override
    public Role searchRoleById(Integer idRole) {
        Role role = template.getForObject(url + "/" + idRole, Role.class);
        return role;
    }

    @Override
    public void saveRole(Role role) {
        if (role.getIdRole() != null && role.getIdRole() > 0) {
            template.put(url, role);
        } else {
            role.setIdRole(0);
            template.postForObject(url, role, String.class);
        }
    }

    @Override
    public void deleteRole(Integer idRole) {
        template.delete(url + "/" + idRole);
    }
}
