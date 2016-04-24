package br.com.fieldrent.repository;

import br.com.fieldrent.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by inafalcao on 2/22/16.
 */
public interface FieldRepository extends JpaRepository<Field, Long> {

    Field findByName(String name);

}
