package br.com.fieldrent.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by inafalcao on 2/29/16.
 */
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotNull
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Transient
    private String photo;

    @Lob
    @Column()
    private byte[] photoLob;

    private boolean monthlySubscriber;

    public User() {
        monthlySubscriber = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getPhotoLob() {
        return photoLob;
    }

    public void setPhotoLob(byte[] photoLob) {
        this.photoLob = photoLob;
    }

    public boolean isMonthlySubscriber() {
        return monthlySubscriber;
    }

    public void setMonthlySubscriber(boolean monthlySubscriber) {
        this.monthlySubscriber = monthlySubscriber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if(user.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
