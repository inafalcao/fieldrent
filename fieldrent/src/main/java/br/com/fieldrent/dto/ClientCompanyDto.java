package br.com.fieldrent.dto;

import br.com.fieldrent.model.ReservationStatus;
import br.com.fieldrent.serialization.DateDeserializer;
import br.com.fieldrent.serialization.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by inafalcao on 2/29/16.
 */
@Entity
@Table
public class ClientCompanyDto extends br.com.fieldrent.model.Entity {

    @NotNull
    @Size(min = 1)
    private String client;

    @NotNull
    @Size(min = 1)
    private String company;

    private Boolean isAdmin;

    public ClientCompanyDto() {

    }

    public ClientCompanyDto(String client, String company, Boolean isAdmin) {
        this.client = client;
        this.company = company;
        this.isAdmin = isAdmin;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
