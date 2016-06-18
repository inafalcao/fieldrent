package br.com.fieldrent.dto;

import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.ClientCompany;

/**
 * Created by inafalcao on 5/28/16.
 */
public class ClientAuthResponseDto {

    private Client client;

    private ClientCompany clientCompany;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientCompany getClientCompany() {
        return clientCompany;
    }

    public void setClientCompany(ClientCompany clientCompany) {
        this.clientCompany = clientCompany;
    }
}
