package br.com.fieldrent.model;

import br.com.fieldrent.util.ByteUtil;
import br.com.fieldrent.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
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
public class Company extends br.com.fieldrent.model.Entity {

    @NotNull
    @Size(min = 1)
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column
    private String address;

    @Column
    private String phone;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String photo;

    @Lob
    @Column(name = "photo")
    @JsonIgnore
    private Byte[] photoLob;

    public Company() {}

    public Company(String name, String cnpj, String address, String phone, String photo) {
        this.name = name;
        this.cnpj = cnpj;
        this.address = address;
        this.phone = phone;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
