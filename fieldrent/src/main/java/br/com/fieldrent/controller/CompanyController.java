package br.com.fieldrent.controller;

import br.com.fieldrent.model.Company;
import br.com.fieldrent.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inafalcao on 1/18/16.
 */

@RestController(value = "company")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Company> list() {
        return companyRepository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add() {
        Company c1 = new Company();
        c1.setName("ina linda");
        companyRepository.save(c1);

    }

}
