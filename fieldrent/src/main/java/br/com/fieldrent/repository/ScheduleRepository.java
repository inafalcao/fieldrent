package br.com.fieldrent.repository;

import br.com.fieldrent.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by inafalcao on 2/22/16.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
