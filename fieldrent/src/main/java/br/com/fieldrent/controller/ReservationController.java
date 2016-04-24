package br.com.fieldrent.controller;

import br.com.fieldrent.model.Company;
import br.com.fieldrent.model.Reservation;
import br.com.fieldrent.repository.CompanyRepository;
import br.com.fieldrent.repository.ReservationRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by inafalcao on 1/18/16.
 */

@RestController
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @RequestMapping(value = "/reservations/{day}", method = RequestMethod.GET)
    public List<Reservation> listByDay(@PathVariable("day") String day) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime dt = formatter.parseDateTime(day);
        List<Reservation> teste = reservationRepository.findByDate(dt);
        return reservationRepository.findByDate(dt);
    }

    @RequestMapping(value = "/reservation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody  Reservation reservation) {
        reservationRepository.save(reservation);
    }

    /*@RequestMapping(value="/company/{id}", method = RequestMethod.GET)
    public Company getOne(@PathVariable("id") Long id) {
        return reservationRepository.findOne(id);
    }



    @RequestMapping(value = "/company/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody Company company) {
        Company existingCompany = reservationRepository.findOne(id);
        company.setId(existingCompany.getId());
        existingCompany = company;
        reservationRepository.save(existingCompany);
    }

    @RequestMapping(value = "/company/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        reservationRepository.delete(id);
    }*/

}
