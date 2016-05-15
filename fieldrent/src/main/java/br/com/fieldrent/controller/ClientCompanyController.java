package br.com.fieldrent.controller;

import br.com.fieldrent.model.ClientCompany;
import br.com.fieldrent.repository.ClientCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by inafalcao on 1/18/16.
 */

@RestController
public class ClientCompanyController {

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    @RequestMapping(value = "/client-company", method = RequestMethod.GET)
    public List<ClientCompany> list() {
        return clientCompanyRepository.findAll();
    }

    @RequestMapping(value = "/client-company", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ClientCompany clientCompany) {
        clientCompanyRepository.save(clientCompany);
    }

    @RequestMapping(value="/client-company/{id}", method = RequestMethod.GET)
    public ClientCompany getOne(@PathVariable("id") Long id) {
        return clientCompanyRepository.findOne(id);
    }


    @RequestMapping(value = "/client-company/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody ClientCompany clientCompany) {
        ClientCompany existingClientCompany = clientCompanyRepository.findOne(id);
        clientCompany.setId(existingClientCompany.getId());
        existingClientCompany = clientCompany;
        clientCompanyRepository.save(existingClientCompany);
    }

    @RequestMapping(value = "/client-company/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        clientCompanyRepository.delete(id);
    }

}
