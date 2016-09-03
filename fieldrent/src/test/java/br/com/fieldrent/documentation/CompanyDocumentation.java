package br.com.fieldrent.documentation;


import br.com.fieldrent.FieldrentApplication;
import br.com.fieldrent.mock.TestMock;
import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.Company;
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
public class CompanyDocumentation {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    TestMock testMock;

    @Before
    public void setUp() {
        testMock.createCompanies();

        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @Test
    public void listCompanies() throws Exception {

        this.mockMvc.perform(get("/companies").accept(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc"))
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
                )
                .andDo(this.document.snippets(
                        requestHeaders(
                                headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                        .description("The token to send with every request."))));

    }

    @Test
    public void getCompany() throws Exception {

        Long companyId = companyRepository.findAll().get(0).getId();
        this.mockMvc.perform(get("/company/{id}", companyId).accept(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc"))
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
                )
                .andDo(this.document.snippets(
                        requestHeaders(
                                headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                        .description("The token to send with every request."))));
    }

    @Test
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
    }

    @Test
    public void updateCompany() throws Exception {
        Company toUpdateCompany = companyRepository.findAll().get(0);
        toUpdateCompany.setName("New name");
        toUpdateCompany.generateBase64PhotoFromPhotoLob();
        toUpdateCompany.setPhotoLob(null);

        ConstrainedFields fields = new ConstrainedFields(Company.class);

        this.mockMvc.perform(put("/company/{id}", toUpdateCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc")
                .content(new Gson().toJson(toUpdateCompany)))
                .andExpect(status().isNoContent())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("id").description(""),
                                fields.withPath("name").description(""),
                                fields.withPath("cnpj").description(""),
                                fields.withPath("address").description(""),
                                fields.withPath("phone").description(""),
                                fields.withPath("photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo")
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
                                        .description("The token to send with every request."))));
    }

    @Test
    public void deleteCompany() throws Exception {
        Long companyId = companyRepository.findAll().get(0).getId();
        this.mockMvc.perform(delete("/company/{id}", companyId).accept(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc"))
                .andExpect(status().isNoContent())
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("id").description("The database entity id"))
                        )
                )
                .andDo(this.document.snippets(
                        requestHeaders(
                                headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                        .description("The token to send with every request."))));
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
