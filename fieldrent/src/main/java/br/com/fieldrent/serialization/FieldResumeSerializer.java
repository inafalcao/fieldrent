package br.com.fieldrent.serialization;

import br.com.fieldrent.model.Field;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;

import java.io.IOException;

/**
 * Created by inafalcao on 4/10/16.
 */
public class FieldResumeSerializer extends JsonSerializer<Field> {

    @Override
    public void serialize(Field field, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String fieldResume = "{\"name\": \"$fieldName\"}";
        String f = fieldResume.replace("$fieldResume", field.getName());
        jsonGenerator.writeString(f);
    }

}