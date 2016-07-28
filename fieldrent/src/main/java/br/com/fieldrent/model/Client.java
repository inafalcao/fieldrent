package br.com.fieldrent.model;

import br.com.fieldrent.model.security.UserAuthority;
import br.com.fieldrent.security.UserRole;
import br.com.fieldrent.util.ByteUtil;
import br.com.fieldrent.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Base64Utils;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by inafalcao on 2/29/16.
 */
@Entity
@Table
public class Client extends br.com.fieldrent.model.Entity implements UserDetails {

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

<<<<<<< HEAD
    @Column(name = "facebook_user")
    private Boolean isFacebookUser = false;
=======







    @NotNull
    private boolean accountExpired;

    @NotNull
    private boolean accountLocked;

    @NotNull
    private boolean credentialsExpired;

    @NotNull
    private boolean accountEnabled;

    @Transient
    private long expires;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UserAuthority> authorities;

    public Client(String email, Date expires) {
        this.email = email;
        this.expires = expires.getTime();
    }

    public Client(String username, String password) {
        this.email = username;
        this.password = password;
    }
>>>>>>> token

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

    @Override
    public String getUsername() {
        return email;
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











    @Override
    @JsonIgnore
    public Set<UserAuthority> getAuthorities() {
        return authorities;
    }

    // Use Roles as external API
    public Set<UserRole> getRoles() {
        Set<UserRole> roles = EnumSet.noneOf(UserRole.class);
        if (authorities != null) {
            for (UserAuthority authority : authorities) {
                roles.add(UserRole.valueOf(authority));
            }
        }
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        for (UserRole role : roles) {
            grantRole(role);
        }
    }

    public void grantRole(UserRole role) {
        if (authorities == null) {
            authorities = new HashSet<UserAuthority>();
        }
        authorities.add(role.asAuthorityFor(this));
    }

    public void revokeRole(UserRole role) {
        if (authorities != null) {
            authorities.remove(role.asAuthorityFor(this));
        }
    }

    public boolean hasRole(UserRole role) {
        if (authorities != null) {
            authorities.contains(role.asAuthorityFor(this));
        }
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getUsername();
    }

}
