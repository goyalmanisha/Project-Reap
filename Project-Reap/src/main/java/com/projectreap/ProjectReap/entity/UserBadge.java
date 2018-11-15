package com.projectreap.ProjectReap.entity;

import com.projectreap.ProjectReap.enums.Role;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;


@Embeddable
public class UserBadge {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
    private Integer goldBadge;
    private Integer silverBadge;
    private Integer bronzeBadge;

    public UserBadge() {
    }

    public UserBadge(Integer goldBadge, Integer silverBadge, Integer bronzeBadge) {
        this.goldBadge = goldBadge;
        this.silverBadge = silverBadge;
        this.bronzeBadge = bronzeBadge;
    }

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public Integer getGoldBadge() {
        return goldBadge;
    }

    public void setGoldBadge(Integer goldBadge) {
        this.goldBadge = goldBadge;
    }

    public Integer getSilverBadge() {
        return silverBadge;
    }

    public void setSilverBadge(Integer silverBadge) {
        this.silverBadge = silverBadge;
    }

    public Integer getBronzeBadge() {
        return bronzeBadge;
    }

    public void setBronzeBadge(Integer bronzeBadge) {
        this.bronzeBadge = bronzeBadge;
    }




    @Override
    public String toString() {
        return "UserBadge{" +

                ", goldBadge=" + goldBadge +
                ", silverBadge=" + silverBadge +
                ", bronzeBadge=" + bronzeBadge +
                '}';
    }
}
