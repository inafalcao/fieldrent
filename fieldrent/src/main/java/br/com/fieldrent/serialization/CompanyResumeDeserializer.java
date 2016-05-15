package br.com.fieldrent.serialization;

import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.Company;
import br.com.fieldrent.repository.CompanyRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by inafalcao on 4/10/16.
 */
public class CompanyResumeDeserializer extends JsonDeserializer<Company> {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String company = jsonParser.getText();
        Company c = companyRepository.findByCnpj(company);
        return c;
    }

}