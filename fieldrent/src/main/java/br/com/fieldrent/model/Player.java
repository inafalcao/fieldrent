package br.com.fieldrent.model;

import br.com.fieldrent.serialization.ClientResumeDeserializer;
import br.com.fieldrent.serialization.ClientResumeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by inafalcao on 2/29/16.
 */
@Entity
@Table
public class Player extends br.com.fieldrent.model.Entity {

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Integer level;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonSerialize(using = ClientResumeSerializer.class)
    @JsonDeserialize(using = ClientResumeDeserializer.class)
    private Client client;

    public Player() {

    }

    public Player(String name, Integer level, Client client) {
        this.name = name;
        this.level = level;
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
