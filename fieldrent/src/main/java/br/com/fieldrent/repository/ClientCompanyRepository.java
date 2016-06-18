package br.com.fieldrent.repository;

import br.com.fieldrent.model.ClientCompany;
import br.com.fieldrent.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by inafalcao on 3/12/16.
 */
public interface ClientCompanyRepository extends JpaRepository<ClientCompany, Long> {

    ClientCompany findByClientEmail(String email);

    //@Query("SELECT cc.company FROM ClientCompany cc where cc.client.email = :email")
    //Company findCompanyByClientEmail( String email);

}
