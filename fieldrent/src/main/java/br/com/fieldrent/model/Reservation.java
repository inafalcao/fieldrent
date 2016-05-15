package br.com.fieldrent.model;

import br.com.fieldrent.serialization.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

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
    @JsonSerialize(using = ClientResumeSerializer.class)
    @JsonDeserialize(using = ClientResumeDeserializer.class)
    private Client client;

    @NotNull
    @ManyToOne
    @JoinColumn(name="field_id", nullable = false)
    @JsonSerialize(using = FieldResumeSerializer.class)
    @JsonDeserialize(using = FieldResumeDeserializer.class)
    private Field field;

    @NotNull
    @Column(name = "date")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private DateTime date;

    @NotNull
    @Column(name = "start_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    @JsonSerialize(using = ScheduleSerializer.class)
    @JsonDeserialize(using = ScheduleDeserializer.class)
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    @JsonSerialize(using = ScheduleSerializer.class)
    @JsonDeserialize(using = ScheduleDeserializer.class)
    private LocalTime endTime;

    @Column(name = "reservation_status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    public Reservation() {

    }

    public Reservation(Client client, Field field, DateTime date, LocalTime startTime, LocalTime endTime, ReservationStatus reservationStatus) {
        this.client = client;
        this.field = field;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservationStatus = reservationStatus;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
