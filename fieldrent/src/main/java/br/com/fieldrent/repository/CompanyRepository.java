package br.com.fieldrent.repository;

import br.com.fieldrent.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by inafalcao on 2/22/16.
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
