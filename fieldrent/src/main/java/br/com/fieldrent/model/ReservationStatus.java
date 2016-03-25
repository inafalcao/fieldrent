package br.com.fieldrent.model;

/**
 * Created by inafalcao on 3/10/16.
 */
public enum ReservationStatus {

    OPEN("Em aberto"),
    CONFIRMED("Confirmado"),
    CANCELED("Cancelado");

    public String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
