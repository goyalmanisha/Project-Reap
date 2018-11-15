package com.projectreap.ProjectReap.pojo;

import javax.persistence.criteria.CriteriaBuilder;

public class UserUpdatedData {

    private Integer userId;
    private String userRole;
    private Boolean userStatus;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }
}
