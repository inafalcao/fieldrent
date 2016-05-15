package br.com.fieldrent.dto;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by inafalcao on 2/29/16.
 */
@Entity
@Table
public class PlayerDto extends br.com.fieldrent.model.Entity {

    @NotNull
    @Size(min = 1)
    private String client;

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    private Integer level;

    public PlayerDto() {

    }

    public PlayerDto(String client, String name, Integer level) {
        this.client = client;
        this.name = name;
        this.level = level;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
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
}
