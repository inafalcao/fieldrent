package br.com.fieldrent.controller;

import br.com.fieldrent.dto.ClientAuthRequestDto;
import br.com.fieldrent.dto.ClientAuthResponseDto;
import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.ClientCompany;
import br.com.fieldrent.repository.ClientCompanyRepository;
import br.com.fieldrent.repository.ClientRepository;
import br.com.fieldrent.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * Created by inafalcao on 3/12/16.
 */

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public List<Client> list() {
        return clientRepository.findAll();
    }

    @RequestMapping(value="/client/{id}", method = RequestMethod.GET)
    public Client getOne(@PathVariable("id") Long id) {
        return clientRepository.findOne(id);
    }

    @RequestMapping(value="/client/email/{email:.+}", method = RequestMethod.GET)
    public Client getByEmail(@PathVariable("email") String email) {
        return clientRepository.findByEmail(email);
    }

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody  Client client) {
        client.grantRole(UserRole.USER);
        clientRepository.save(client);
    }

    @RequestMapping(value = "/client/auth", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ClientAuthResponseDto auth(@RequestBody ClientAuthRequestDto clientToAuth) {
        Client client = clientRepository.findByEmailAndPassword(clientToAuth.getEmail(), clientToAuth.getPassword());
        ClientCompany clientCompany = clientCompanyRepository.findByClientEmail(clientToAuth.getEmail());

        ClientAuthResponseDto response = new ClientAuthResponseDto();

        if (clientCompany != null)
            response.setClientCompany(clientCompany);
        else if(client != null)
            response.setClient(client);
        else
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);

        return response;
    }

    @RequestMapping(value="/client/facebook/{email:.+}", method = RequestMethod.GET)
    public boolean isFacebookUser(@PathVariable("email") String email) {
        Client client = clientRepository.findByEmail(email);

        if(client != null)
            return client.getIsFacebookUser();
        return false; // TODO: deveria dar um 404, eu acho
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody Client client) {
        Client existingClient = clientRepository.findOne(id);
        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        existingClient.setPhone(client.getPhone());
        existingClient.setMonthlySubscriber(client.getMonthlySubscriber());
        existingClient.setPhoto(client.getPhoto());
        existingClient.setIsFacebookUser(client.getIsFacebookUser());
        clientRepository.save(existingClient);
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        clientRepository.delete(id);
    }

    @RequestMapping(value = "/client/email/{email:.+}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByEmail(@PathVariable("email") String email) {
        clientRepository.deleteByEmail(email);
    }

}
