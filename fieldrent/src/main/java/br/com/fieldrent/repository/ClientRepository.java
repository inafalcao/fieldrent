package br.com.fieldrent.repository;

import br.com.fieldrent.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by inafalcao on 3/12/16.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

    void deleteByEmail(String email);

    Client findByEmail(String email);

}
