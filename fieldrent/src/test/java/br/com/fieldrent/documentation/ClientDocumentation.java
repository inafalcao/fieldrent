package br.com.fieldrent.documentation;


import br.com.fieldrent.FieldrentApplication;
import br.com.fieldrent.dto.ClientAuthRequestDto;
import br.com.fieldrent.dto.ClientRequestDto;
import br.com.fieldrent.mock.TestMock;
import br.com.fieldrent.model.Client;
import br.com.fieldrent.repository.ClientRepository;
import br.com.fieldrent.repository.CompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
public class ClientDocumentation {
    
    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    TestMock testMock;

    @Before
    public void setUp() {
        testMock.createClients();
        testMock.createCompanies();

        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    /////////////////| Client |////////////////


    //TODO: Substituir por método com offset e limit
    @Test
    public void listClients() throws Exception {

        this.mockMvc.perform(get("/clients").accept(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc"))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                // Password should not be sent back
                                fieldWithPath("[].id").description("Database id."),
                                fieldWithPath("[].name").description(""),
                                fieldWithPath("[].email").description(""),
                                fieldWithPath("[].phone").description(""),
                                fieldWithPath("[].photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo"),
                                fieldWithPath("[].monthlySubscriber").description("Mensalista"),
                                fieldWithPath("[].isFacebookUser").description(""),
                                fieldWithPath("[].authorities").description("Can be either: ROLE_USER, ROLE_ADMIN, or Both.")
                        )
                        )
                )
                .andDo(this.document.snippets(
                        requestHeaders(
                                headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                        .description("The token to be sent with every request."))));
    }

    @Test
    public void getClient() throws Exception {

        Long clientId = clientRepository.findAll().get(0).getId();
        this.mockMvc.perform(get("/client/{id}", clientId)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                // Password should not be sent back
                                fieldWithPath("id").description("Database id."),
                                fieldWithPath("name").description(""),
                                fieldWithPath("email").description(""),
                                fieldWithPath("phone").description(""),
                                fieldWithPath("photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo"),
                                fieldWithPath("monthlySubscriber").description("Mensalista"),
                                fieldWithPath("isFacebookUser").description(""),
                                fieldWithPath("authorities").description("Can be either: ROLE_USER, ROLE_ADMIN, or Both.")
                        )
                       )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("id").description("The database entity id"))
                        )
                )
                .andDo(this.document.snippets(
                    requestHeaders(
                            headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                    .description("The token to be sent with every request."))));
    }

    @Test
    public void getClientByEmail() throws Exception {

        String clientEmail = clientRepository.findAll().get(0).getEmail();
        this.mockMvc.perform(get("/client/email/{email}/", clientEmail).accept(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc"))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                // Password should not be sent back
                                fieldWithPath("id").description("Database id."),
                                fieldWithPath("name").description(""),
                                fieldWithPath("email").description(""),
                                fieldWithPath("phone").description(""),
                                fieldWithPath("photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo"),
                                fieldWithPath("monthlySubscriber").description("Mensalista"),
                                fieldWithPath("isFacebookUser").description(""),
                                fieldWithPath("authorities").description("Can be either: ROLE_USER, ROLE_ADMIN, or Both."))
                        )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("email").description("Unique client e-mail"))
                        )
                )
                .andDo(this.document.snippets(
                        requestHeaders(
                                headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                        .description("The token to be sent with every request."))));
    }

    @Test
    public void createClient() throws Exception {
        ClientRequestDto mockClient = new ClientRequestDto("Client", "passwd", "client3@email.com", "99999999", true, "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf", true);

        ConstrainedFields fields = new ConstrainedFields(ClientRequestDto.class);

        this.mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(mockClient)))
                .andExpect(status().isCreated())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("name").description(""),
                                fields.withPath("email").description(""),
                                fields.withPath("password").description(""),
                                fields.withPath("phone").description(""),
                                fields.withPath("photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo"),
                                fields.withPath("monthlySubscriber").description("Mensalista"),
                                fields.withPath("isFacebookUser").description("")
                        )
                        )
                );
    }

    /*@Test
    public void authenticateClient() throws Exception {
        Client client = clientRepository.findAll().get(0);
        ClientAuthRequestDto clientAuthDto = new ClientAuthRequestDto(client.getEmail(), client.getPassword());

        ConstrainedFields fields = new ConstrainedFields(ClientAuthRequestDto.class);

        this.mockMvc.perform(post("/client/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(clientAuthDto)))
                .andExpect(status().isAccepted())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("email").description(""),
                                fields.withPath("password").description(""))
                        )

                )
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("client").description("Return just a Client, if it is not a ClientCompany."),
                                fieldWithPath("clientCompany").description("Return just a ClientCompany, if it is not a common client.")
                        )
                        )
                );

    }*/

    /*@Test
    public void login() throws Exception {
        ClientAuthRequestDto clientAuthDto = new ClientAuthRequestDto("client@email.com", "field@123!");

        ConstrainedFields fields = new ConstrainedFields(ClientAuthRequestDto.class);

        this.mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(clientAuthDto)))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("email").description(""),
                                fields.withPath("password").description(""))
                        )
                )
                .andDo(this.document.snippets(
                        responseHeaders(
                                headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                .description("The token to send with every request."))));
    }*/

    @Test
    public void updateClient() throws Exception {
        Client toUpdateClient = clientRepository.findAll().get(0);

        ClientRequestDto mockClient = new ClientRequestDto(
                toUpdateClient.getName(),
                null,
                toUpdateClient.getEmail(),
                toUpdateClient.getPhone(),
                toUpdateClient.getMonthlySubscriber(),
                toUpdateClient.getPhoto(),
                toUpdateClient.getIsFacebookUser());
        mockClient.setId(toUpdateClient.getId());
        mockClient.setName("New name");
        mockClient.setPassword("");

        ConstrainedFields fields = new ConstrainedFields(ClientRequestDto.class);

        this.mockMvc.perform(put("/client/{id}", mockClient.getId())
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(mockClient)))
                .andExpect(status().isNoContent())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("id").description(""),
                                fields.withPath("name").description(""),
                                fields.withPath("email").description(""),
                                fields.withPath("password")
                                        .description("Passwords should not be sent " +
                                                "to update. If you do send, it shall be ignored. " +
                                                "Use <<todo-snippet, todo description>> instead."),
                                fields.withPath("phone").description(""),
                                fields.withPath("photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo"),
                                fields.withPath("monthlySubscriber").description("Mensalista"),
                                fields.withPath("isFacebookUser").description("")
                        )
                        )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("id").description("The database entity id"))
                        ) )
                .andDo(this.document.snippets(
                    requestHeaders(headerWithName(FieldRentConstants.AUTH_HEADER_NAME).description("The token to be sent with every request."))));
    }

    @Test
    public void deleteClient() throws Exception {
        Long clientId = clientRepository.findAll().get(0).getId();
        this.mockMvc.perform(delete("/client/{id}", clientId).accept(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc"))
                .andExpect(status().is2xxSuccessful())
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("id").description("The database entity id"))
                        )
                )
                .andDo(this.document.snippets(
                        requestHeaders(
                                headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                        .description("The token to be sent with every request."))));
    }

    @Test
    public void deleteClientByEmail() throws Exception {
        String clientEmail = clientRepository.findAll().get(0).getEmail();
        this.mockMvc.perform(delete("/client/email/{email}", clientEmail).accept(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc"))
                .andExpect(status().is2xxSuccessful())
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("email").description("Unique e-mail"))
                        )
                )
                .andDo(this.document.snippets(
                        requestHeaders(
                                headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                        .description("The token to be sent with every request."))));
    }

    /////////////////| Company |////////////////

    @Test
    public void listCompanies() throws Exception {

        this.mockMvc.perform(get("/companies").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("[].id").description("Database id."),
                                fieldWithPath("[].name").description(""),
                                fieldWithPath("[].cnpj").description(""),
                                fieldWithPath("[].address").description(""),
                                fieldWithPath("[].phone").description(""),
                                fieldWithPath("[].photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo")
                        )
                        )
                );

    }

    @Test
    public void getCompany() throws Exception {

        Long companyId = companyRepository.findAll().get(0).getId();
        this.mockMvc.perform(get("/company/{id}", companyId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("id").description("Database id."),
                                fieldWithPath("name").description(""),
                                fieldWithPath("cnpj").description(""),
                                fieldWithPath("address").description(""),
                                fieldWithPath("phone").description(""),
                                fieldWithPath("photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo")
                        )
                        )
                )
                .andDo(this.document.snippets(
                    pathParameters(
                            parameterWithName("id").description("The database entity id"))
                    )
                );
    }

    /*@Test
    public void createCompany() throws Exception {
        Company mock = testMock.createCompany();

        ConstrainedFields fields = new ConstrainedFields(Company.class);

        this.mockMvc.perform(post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(mock)))
                .andExpect(status().isCreated())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("name").description(""),
                                fields.withPath("cnpj").description(""),
                                fields.withPath("address").description(""),
                                fields.withPath("phone").description(""),
                                fields.withPath("photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo")
                        )
                        )
                );
    }*/


    ////////////////////////////////////////////


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
