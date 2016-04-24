package br.com.fieldrent.model;

/**
 * Created by inafalcao on 3/10/16.
 */
public enum ScheduleStatus {

    AVAILABLE("Disponível"),
    RESERVED("Reservado"),
    UNAVAILABLE("Indisponível");

    public String description;

    ScheduleStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
