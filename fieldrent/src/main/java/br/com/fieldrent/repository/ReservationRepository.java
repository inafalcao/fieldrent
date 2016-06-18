package br.com.fieldrent.repository;

import br.com.fieldrent.model.Reservation;
import br.com.fieldrent.model.ReservationStatus;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by inafalcao on 2/22/16.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByDate(DateTime dateTime);

    List<Reservation> findByDateAndFieldCompanyCnpj(DateTime dateTime, String cnpj);

    List<Reservation> findByReservationStatus(ReservationStatus status);

    List<Reservation> findByFieldCompanyCnpj(String cnpj);

    List<Reservation> findByFieldCompanyCnpjAndReservationStatus(String cnpj, ReservationStatus status);

    List<Reservation> findByClientEmail(String email);
}
