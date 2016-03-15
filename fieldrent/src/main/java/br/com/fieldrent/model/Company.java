package br.com.fieldrent.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by inafalcao on 1/17/16.
 */
@Entity
@Table
public class Company extends br.com.fieldrent.model.Entity {

    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String cnpj;

    // TODO: maybe better not to have this collections
    @OneToMany(mappedBy="company")
    private List<Field> fields;

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

}
