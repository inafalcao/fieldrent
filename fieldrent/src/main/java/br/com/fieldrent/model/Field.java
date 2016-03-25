package br.com.fieldrent.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

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
    private String photo;

    @Lob
    @Column()
    private Byte[] photoLob;

    @NotNull
    @ManyToOne
    @JoinColumn(name="company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy="field")
    private List<Schedule> schedules;

}
