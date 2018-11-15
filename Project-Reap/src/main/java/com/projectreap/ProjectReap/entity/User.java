package com.projectreap.ProjectReap.entity;

import com.projectreap.ProjectReap.enums.Badge;
import com.projectreap.ProjectReap.enums.Role;
import org.hibernate.boot.model.source.spi.ColumnBindingDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "Please provide your first name")
    @NotNull
    private String firstName;

    @NotEmpty(message = "Please provide your last name")
    private String lastName;

    @NotNull
    @Column(unique = true)
    private String userName;

    @Email(message = "Please provide a valid e-mail")
    @NotEmpty(message = "Please provide an e-mail")
    @NotNull
    private String email;

    @NotNull
    private String password;

//    @NotNull
    //@Enumerated(EnumType.STRING)
    private String  role="user";

//    @ElementCollection
//    List<UserBadge> badges=new ArrayList<>();

    @Embedded
    UserBadge badge;

    @Column(name = "IsActive", columnDefinition = "boolean default true")
    Boolean status=true;

    @OneToMany
    @JoinTable(joinColumns = @JoinColumn(name="USER_ID")
            ,inverseJoinColumns = @JoinColumn(name = "APPRECIATION_ID"))
    List<Appreciation> appreciationList=new ArrayList<>();

    public User() {
    }

    public User(Integer id,@NotEmpty(message = "Please provide your first name") @NotNull String firstName, @NotEmpty(message = "Please provide your last name") String lastName, @NotNull String userName, @Email(message = "Please provide a valid e-mail") @NotEmpty(message = "Please provide an e-mail") @NotNull String email, @NotNull String password, String role, UserBadge badge, Boolean status) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.badge = badge;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String  getRole() {
        return role;
    }

    public void setRole(String  role) {
        this.role = role;
    }

    public UserBadge getBadge() {
        return badge;
    }

    public void setBadge(UserBadge badge) {
        this.badge = badge;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", badge=" + badge +
                ", status=" + status +
                '}';
    }
}
