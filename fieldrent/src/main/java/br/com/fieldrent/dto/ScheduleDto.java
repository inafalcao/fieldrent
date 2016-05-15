package br.com.fieldrent.dto;

import br.com.fieldrent.model.ScheduleStatus;
import br.com.fieldrent.serialization.ScheduleDeserializer;
import br.com.fieldrent.serialization.ScheduleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by inafalcao on 4/30/16.
 */
public class ScheduleDto {

    @NotNull
    @Size(min = 1)
    private String startTime;

    @NotNull
    @Size(min = 1)
    private String endTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    public ScheduleDto(String startTime, String endTime, ScheduleStatus status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
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

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }
}
