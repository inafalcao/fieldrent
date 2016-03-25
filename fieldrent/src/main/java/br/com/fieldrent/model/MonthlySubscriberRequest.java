package br.com.fieldrent.model;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by inafalcao on 2/29/16.
 */
public class MonthlySubscriberRequest {

    @NotNull
    @OneToOne
    @Column(nullable = false, unique = true)
    private Client client;

    private Boolean confirmed;

}
