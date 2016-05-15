package br.com.fieldrent.documentation;


import br.com.fieldrent.FieldrentApplication;
import br.com.fieldrent.dto.ClientCompanyDto;
import br.com.fieldrent.mock.TestMock;
import br.com.fieldrent.model.ClientCompany;
import br.com.fieldrent.repository.ClientCompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by inafalcao on 3/13/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FieldrentApplication.class)
@WebAppConfiguration
@Transactional
public class ClientCompanyDocumentation {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    @Autowired
    TestMock testMock;

    @Before
    public void setUp() {
        testMock.createClients();
        testMock.createCompanies();
        testMock.createClientsCompany();

        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @Test
    public void listClientCompany() throws Exception {

        this.mockMvc.perform(get("/client-company/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                                responseFields(
                                        fieldWithPath("[].id").description("Database id."),
                                        fieldWithPath("[].client").description("Just the client e-mail"),
                                        fieldWithPath("[].company").description("Just the company CNPJ"),
                                        fieldWithPath("[].isAdmin").description("")
                                )
                        )
                );
    }

    @Test
    public void getClientCompany() throws Exception {

        Long clientCompanyId = clientCompanyRepository.findAll().get(0).getId();
        this.mockMvc.perform(get("/client-company/{id}", clientCompanyId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("id").description("Database id."),
                                fieldWithPath("client").description("Just the client e-mail"),
                                fieldWithPath("company").description("Just the company CNPJ"),
                                fieldWithPath("isAdmin").description("")
                        )
                        )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("id").description("The database entity id"))
                        )
                );
    }

    @Test
    public void createClientCompany() throws Exception {

        ConstrainedFields fields = new ConstrainedFields(ClientCompanyDto.class);
        this.mockMvc.perform(post("/client-company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMock.createClientCompany()))
                .andExpect(status().isCreated())
                .andDo(this.document.snippets(
                                requestFields(
                                        fields.withPath("client").description("Just the client e-mail"),
                                        fields.withPath("company").description("Just the company CNPJ"),
                                        fields.withPath("isAdmin").description("")
                                )
                        )
                );

    }

    @Test
    public void updateClientCompany() throws Exception {
        ClientCompany toUpdateClientCompany = clientCompanyRepository.findAll().get(0);
        ConstrainedFields fields = new ConstrainedFields(ClientCompanyDto.class);

        this.mockMvc.perform(put("/client-company/{id}", toUpdateClientCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMock.updateClientCompany(toUpdateClientCompany)))
                .andExpect(status().isNoContent())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("id").description(""),
                                fields.withPath("client").description("Just the client e-mail"),
                                fields.withPath("company").description("Just the company CNPJ"),
                                fields.withPath("isAdmin").description("")
                        )
                        )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("id").description("The database entity id"))
                        )
                );
    }

    @Test
    public void deleteClientCompany() throws Exception {
        Long clientCompanyId = clientCompanyRepository.findAll().get(0).getId();
        this.mockMvc.perform(delete("/client-company/{id}", clientCompanyId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("id").description("The database entity id"))
                        )
                );
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }

    }

}
