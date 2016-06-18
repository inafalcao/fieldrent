package br.com.fieldrent.repository;

import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by inafalcao on 2/22/16.
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByCnpj(String cnpj);

    @Query("SELECT cc.company FROM ClientCompany cc where cc.client.email = :email")
    Company findByClientEmail(@Param("email") String email);
}
