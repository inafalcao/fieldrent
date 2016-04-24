package br.com.fieldrent.serialization;

import br.com.fieldrent.model.Field;
import br.com.fieldrent.repository.FieldRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by inafalcao on 4/10/16.
 */
public class FieldResumeDeserializer extends JsonDeserializer<Field> {

    @Autowired
    private FieldRepository fieldRepository;

    @Override
    public Field deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String field = jsonParser.getText();
        Field f = fieldRepository.findByName(field);
        return f;
    }

}