package br.com.fieldrent.model;

import br.com.fieldrent.util.ByteUtil;
import br.com.fieldrent.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Base64Utils;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by inafalcao on 1/17/16.
 */
@Entity
@Table
public class Field extends br.com.fieldrent.model.Entity {

    @NotNull
    @Size(min = 1)
    @Column(nullable = false, unique = true)
    private String name;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String photo;

    @Lob
    @Column(name = "photo")
    @JsonIgnore
    private Byte[] photoLob;

    @NotNull
    @ManyToOne
    @JoinColumn(name="company_id", nullable = false)
    private Company company;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "field_id")
    private List<Schedule> schedules;

    public Field(String name, String photo, Company company, List<Schedule> schedules) {
        this.name = name;
        this.photo = photo;
        this.company = company;
        this.schedules = schedules;
    }

    public Field(String name, String photo, Company company) {
        this.name = name;
        this.photo = photo;
        this.company = company;
    }

    public Field() {}

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
