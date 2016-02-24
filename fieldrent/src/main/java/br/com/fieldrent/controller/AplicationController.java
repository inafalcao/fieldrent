package br.com.fieldrent.controller;

import br.com.fieldrent.model.Company;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by inafalcao on 1/17/16.
 */

@RestController
public class AplicationController {

    @RequestMapping("/")
    public Company root() {
        Company c = new Company();
        c.setName("ina linda");

        return c;
    }

}
