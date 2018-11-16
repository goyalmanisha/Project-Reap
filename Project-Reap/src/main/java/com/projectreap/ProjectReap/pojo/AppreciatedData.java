package com.projectreap.ProjectReap.pojo;

import javax.persistence.criteria.CriteriaBuilder;

public class AppreciatedData {

    private Integer userId;
private Integer appreciatedUser;
    private Integer appreciatedBy;
    private String badge;
    private String karma;
    private String reason;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAppreciatedUser() {
        return appreciatedUser;
    }

    public void setAppreciatedUser(Integer appreciatedUser) {
        this.appreciatedUser = appreciatedUser;
    }

    public Integer getAppreciatedBy() {
        return appreciatedBy;
    }

    public void setAppreciatedBy(Integer appreciatedBy) {
        this.appreciatedBy = appreciatedBy;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getKarma() {
        return karma;
    }

    public void setKarma(String karma) {
        this.karma = karma;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "AppreciatedData{" +
                "userId='" + userId + '\'' +
                ", appreciatedUser='" + appreciatedUser + '\'' +
                ", appreciatedBy='" + appreciatedBy + '\'' +
                ", badge='" + badge + '\'' +
                ", karma='" + karma + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }

}
