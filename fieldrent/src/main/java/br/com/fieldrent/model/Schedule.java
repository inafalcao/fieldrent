package br.com.fieldrent.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by inafalcao on 3/10/16.
 */
@Entity
@Table
public class Schedule extends br.com.fieldrent.model.Entity {

    @NotNull
    @ManyToOne()
    @JoinColumn(name="field_id")
    private Field field;

    @NotNull
    @Column(name = "start_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @Column(name = "busy")
    private Boolean isBusy;
}
