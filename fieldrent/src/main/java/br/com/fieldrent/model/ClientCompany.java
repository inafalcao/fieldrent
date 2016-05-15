package br.com.fieldrent.model;

import br.com.fieldrent.serialization.ClientResumeDeserializer;
import br.com.fieldrent.serialization.ClientResumeSerializer;
import br.com.fieldrent.serialization.CompanyResumeDeserializer;
import br.com.fieldrent.serialization.CompanyResumeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by inafalcao on 2/29/16.
 */

@javax.persistence.Entity
@Table
public class ClientCompany extends Entity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonSerialize(using = ClientResumeSerializer.class)
    @JsonDeserialize(using = ClientResumeDeserializer.class)
    private Client client;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonSerialize(using = CompanyResumeSerializer.class)
    @JsonDeserialize(using = CompanyResumeDeserializer.class)
    private Company company;

    @Column(name = "admin")
    private Boolean isAdmin;

    public ClientCompany() {

    }

    public ClientCompany(Client client, Company company, Boolean isAdmin) {
        this.client = client;
        this.company = company;
        this.isAdmin = isAdmin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
