package br.com.fieldrent.serialization;

import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.Company;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by inafalcao on 4/10/16.
 */
public class CompanyResumeSerializer extends JsonSerializer<Company> {

    @Override
    public void serialize(Company company, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String companyResume = company.getCnpj();
        jsonGenerator.writeString(companyResume);
    }

}