package br.com.fieldrent.repository;

import br.com.fieldrent.model.ClientCompany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by inafalcao on 3/12/16.
 */
public interface ClientCompanyRepository extends JpaRepository<ClientCompany, Long> {


}
