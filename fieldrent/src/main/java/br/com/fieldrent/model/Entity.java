package br.com.fieldrent.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by inafalcao on 2/29/16.
 */
@javax.persistence.Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class Entity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entity entity = (Entity) o;
        if(entity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
