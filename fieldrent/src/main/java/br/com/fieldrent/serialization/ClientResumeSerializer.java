package br.com.fieldrent.serialization;

import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.Field;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by inafalcao on 4/10/16.
 */
public class ClientResumeSerializer extends JsonSerializer<Client> {

    @Override
    public void serialize(Client client, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String clientResume = client.getEmail();
        //String c = clientResume.replace("$clientEmail", client.getEmail());
        jsonGenerator.writeString(clientResume);
    }

}