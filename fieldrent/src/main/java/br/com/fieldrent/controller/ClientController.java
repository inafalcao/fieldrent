package br.com.fieldrent.controller;

import br.com.fieldrent.model.Client;
import br.com.fieldrent.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by inafalcao on 3/12/16.
 */

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public List<Client> list() {
        return clientRepository.findAll();
    }

    @RequestMapping(value="/client/{id}", method = RequestMethod.GET)
    public Client getOne(@PathVariable("id") Long id) {
        return clientRepository.findOne(id);
    }

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody  Client client) {
        clientRepository.save(client);
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody Client client) {
        Client existingClient = clientRepository.findOne(id);
        client.setId(existingClient.getId());
        // Do not change passwords via update
        client.setPassword(existingClient.getPassword());
        existingClient = client;
        clientRepository.save(existingClient);
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        clientRepository.delete(id);
    }

    // TODO: document delete client by e-mail
    @RequestMapping(value = "/client/email/{email}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByEmail(@PathVariable("email") String email) {
        clientRepository.deleteByEmail(email);
    }

}
