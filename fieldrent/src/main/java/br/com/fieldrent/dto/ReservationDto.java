package br.com.fieldrent.dto;

import br.com.fieldrent.model.*;
import br.com.fieldrent.serialization.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by inafalcao on 2/29/16.
 */
@Entity
@Table
public class ReservationDto extends br.com.fieldrent.model.Entity {

    @NotNull
    @Size(min = 1)
    private String client;

    @NotNull
    @Size(min = 1)
    private String field;

    @NotNull
    @Size(min = 1)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private String date;

    @NotNull
    @Size(min = 1)
    private String startTime;

    @NotNull
    @Size(min = 1)
    private String endTime;

    @Column(name = "reservation_status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    public ReservationDto() {

    }

    public ReservationDto(String client, String field, String date, String startTime, String endTime, ReservationStatus reservationStatus) {
        this.client = client;
        this.field = field;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservationStatus = reservationStatus;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
