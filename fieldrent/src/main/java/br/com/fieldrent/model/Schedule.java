package br.com.fieldrent.model;

import br.com.fieldrent.serialization.ScheduleDeserializer;
import br.com.fieldrent.serialization.ScheduleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    /*@NotNull
    @ManyToOne()
    @JoinColumn(name="field_id")
    private Field field;*/

    @NotNull
    @Column(name = "start_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonSerialize(using = ScheduleSerializer.class)
    @JsonDeserialize(using = ScheduleDeserializer.class)
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonSerialize(using = ScheduleSerializer.class)
    @JsonDeserialize(using = ScheduleDeserializer.class)
    private LocalTime endTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    public Schedule(LocalTime startTime, LocalTime endTime, ScheduleStatus status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Schedule() {}

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

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }
}
