package br.com.fieldrent.serialization;

import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.Field;
import br.com.fieldrent.repository.ClientRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by inafalcao on 4/10/16.
 */
public class ClientResumeDeserializer extends JsonDeserializer<Client> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String client = jsonParser.getText();
        Client c = clientRepository.findByEmail(client);
        return c;
    }

}