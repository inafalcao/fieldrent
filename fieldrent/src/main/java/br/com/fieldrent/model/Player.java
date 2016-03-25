package br.com.fieldrent.model;

import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by inafalcao on 2/29/16.
 */
@Entity
@Table
public class Player extends br.com.fieldrent.model.Entity {

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String level;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Client client;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
