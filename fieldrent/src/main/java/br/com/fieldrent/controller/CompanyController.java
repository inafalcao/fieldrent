package br.com.fieldrent.controller;

import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.Company;
import br.com.fieldrent.repository.ClientRepository;
import br.com.fieldrent.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inafalcao on 1/18/16.
 */

@RestController
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public List<Company> list() {
        return companyRepository.findAll();
    }

    @RequestMapping(value="/company/{id}", method = RequestMethod.GET)
    public Company getOne(@PathVariable("id") Long id) {
        return companyRepository.findOne(id);
    }

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody  Company company) {
        companyRepository.save(company);
    }

    @RequestMapping(value = "/company/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody Company company) {
        Company existingCompany = companyRepository.findOne(id);
        company.setId(existingCompany.getId());
        existingCompany = company;
        companyRepository.save(existingCompany);
    }

    @RequestMapping(value = "/company/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        companyRepository.delete(id);
    }

}
