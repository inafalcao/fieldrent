package br.com.fieldrent.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by inafalcao on 8/17/16.
 */
public class ClientRequestDto extends br.com.fieldrent.model.Entity {

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String photo;


    @Column(name = "monthly_subscriber")
    private Boolean monthlySubscriber;

    @Column(name = "facebook_user")
    private Boolean isFacebookUser = false;


    public ClientRequestDto(String name, String password, String email, String phone, Boolean monthlySubscriber, String photo, Boolean isFacebookUser) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.monthlySubscriber = monthlySubscriber;
        this.photo = photo;
        this.isFacebookUser = isFacebookUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getMonthlySubscriber() {
        return monthlySubscriber;
    }

    public void setMonthlySubscriber(Boolean monthlySubscriber) {
        this.monthlySubscriber = monthlySubscriber;
    }

    public Boolean getFacebookUser() {
        return isFacebookUser;
    }

    public void setFacebookUser(Boolean facebookUser) {
        isFacebookUser = facebookUser;
    }
}
