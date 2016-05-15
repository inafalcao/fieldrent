package br.com.fieldrent.repository;

import br.com.fieldrent.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by inafalcao on 3/12/16.
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
