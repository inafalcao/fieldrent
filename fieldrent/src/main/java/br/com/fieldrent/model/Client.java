package br.com.fieldrent.model;

import br.com.fieldrent.util.ByteUtil;
import br.com.fieldrent.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Base64Utils;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by inafalcao on 2/29/16.
 */
@Entity
@Table
public class Client extends br.com.fieldrent.model.Entity {

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

    @Lob
    @Column(name = "photo")
    @JsonIgnore
    private Byte[] photoLob;

    @Column(name = "monthly_subscriber")
    private Boolean monthlySubscriber;

    @Column(name = "facebook_user")
    private Boolean isFacebookUser = false;

    public Client() {
        monthlySubscriber = false;
        isFacebookUser = false;
    }

    public Client(String name, String password, String email, String phone, Boolean monthlySubscriber, String photo, Boolean isFacebookUser) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.monthlySubscriber = monthlySubscriber;
        this.photo = photo;
        this.isFacebookUser = isFacebookUser;
    }

    public Boolean getIsFacebookUser() {
        return isFacebookUser;
    }

    public void setIsFacebookUser(Boolean facebookUser) {
        isFacebookUser = facebookUser;
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
        generateBase64PhotoFromPhotoLob();
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
        generatePhotoLobFromBase64Photo();
    }

    public void generateBase64PhotoFromPhotoLob() {
        if(photoLob != null)
            photo = Base64Utils.encodeToString(ByteUtil.boxedToPrimitiveArray(photoLob));
    }

    public Byte[] getPhotoLob() {
        generatePhotoLobFromBase64Photo();
        return photoLob;
    }

    public void generatePhotoLobFromBase64Photo() {
        if(!StringUtil.isNullOrEmpty(photo))
            photoLob = ByteUtil.primitiveToBoxedArray(Base64Utils.decodeFromString(photo));
    }

    public void setPhotoLob(Byte[] photoLob) {
        this.photoLob = photoLob;
        generateBase64PhotoFromPhotoLob();
    }

    public Boolean getMonthlySubscriber() {
        return monthlySubscriber;
    }

    public Boolean isMonthlySubscriber() {
        return monthlySubscriber;
    }

    public void setMonthlySubscriber(Boolean monthlySubscriber) {
        this.monthlySubscriber = monthlySubscriber;
    }

}
