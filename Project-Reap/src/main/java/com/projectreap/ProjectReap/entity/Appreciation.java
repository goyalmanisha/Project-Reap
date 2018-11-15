package com.projectreap.ProjectReap.entity;

import com.projectreap.ProjectReap.enums.Badge;
import com.projectreap.ProjectReap.pojo.AppreciatedData;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Appreciation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @ManyToOne
    private User appreciatedUser;

    @ManyToOne
    private User appreciatedBy;

    @Enumerated(EnumType.STRING)
    private Badge badge;

    private String karma;

    private String karmaReason;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getAppreciatedUser() {
        return appreciatedUser;
    }

    public void setAppreciatedUser(User appreciatedUser) {
        this.appreciatedUser = appreciatedUser;
    }

    public User getAppreciatedBy() {
        return appreciatedBy;
    }

    public void setAppreciatedBy(User appreciatedBy) {
        this.appreciatedBy = appreciatedBy;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public String getKarma() {
        return karma;
    }

    public void setKarma(String karma) {
        this.karma = karma;
    }

    public String getKarmaReason() {
        return karmaReason;
    }

    public void setKarmaReason(String karmaReason) {
        this.karmaReason = karmaReason;
    }


}
