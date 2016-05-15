package br.com.fieldrent.repository;

import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by inafalcao on 2/22/16.
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByCnpj(String cnpj);

}
