package br.com.fieldrent.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalTime;

import java.io.IOException;

/**
 * Created by inafalcao on 4/10/16.
 */
public class ScheduleSerializer extends JsonSerializer<LocalTime> {

    @Override
    public void serialize(LocalTime localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(localDate.toString("HH:mm"));
    }

}