package com.practices.sergiodelamata.filmsFrontend.model;

public class Role {
    private Integer idRole;
    private String authority;

    public Role() {
    }

    public Role(String idRoleAndName){
        if(idRoleAndName != null && idRoleAndName.length() > 0){
            String[] fieldPositions = idRoleAndName.split("-");
            this.idRole = Integer.parseInt(fieldPositions[0]);
            this.authority = fieldPositions[1];
        }
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return ""+idRole+"-"+this.authority;
    }

}
