package br.com.fieldrent.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by inafalcao on 2/29/16.
 */
@Entity
@Table
public class Reservation extends br.com.fieldrent.model.Entity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Client client;

    @NotNull
    @ManyToOne
    @JoinColumn(name="field_id", nullable = false)
    private Field field;

    @NotNull
    @Column(name = "start_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private DateTime date;

    @Column(name = "reservation_status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

}
