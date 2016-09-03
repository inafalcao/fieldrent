package br.com.fieldrent.documentation;


import br.com.fieldrent.FieldrentApplication;
import br.com.fieldrent.dto.PlayerDto;
import br.com.fieldrent.mock.TestMock;
import br.com.fieldrent.model.Player;
import br.com.fieldrent.repository.PlayerRepository;
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

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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
public class PlayerDocumentation {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    TestMock testMock;

    @Before
    public void setUp() {
        testMock.createClients();
        testMock.createPlayers();

        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @Test
    public void listPlayers() throws Exception {

        this.mockMvc.perform(get("/player/").accept(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc"))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                                responseFields(
                                        fieldWithPath("[].id").description("Database id."),
                                        fieldWithPath("[].client").description("Just the client e-mail"),
                                        fieldWithPath("[].name").description(""),
                                        fieldWithPath("[].level").description("")
                                )
                        )
                )
                .andDo(this.document.snippets(
                        requestHeaders(
                                headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                        .description("The token to send with every request."))));
    }

    @Test
    public void getPlayer() throws Exception {

        Long playerId = playerRepository.findAll().get(0).getId();
        this.mockMvc.perform(get("/player/{id}", playerId).accept(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc"))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("id").description("Database id."),
                                fieldWithPath("client").description("Just the client e-mail"),
                                fieldWithPath("name").description(""),
                                fieldWithPath("level").description("")
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
    public void createPlayer() throws Exception {

        ConstrainedFields fields = new ConstrainedFields(PlayerDto.class);
        this.mockMvc.perform(post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc")
                .content(testMock.createPlayer()))
                .andExpect(status().isCreated())
                .andDo(this.document.snippets(
                                requestFields(
                                        fields.withPath("client").description("Just the client e-mail"),
                                        fields.withPath("name").description(""),
                                        fields.withPath("level").description("")
                                )
                        )
                )
                .andDo(this.document.snippets(
                        requestHeaders(
                                headerWithName(FieldRentConstants.AUTH_HEADER_NAME)
                                        .description("The token to send with every request."))));

    }

    @Test
    public void updatePlayer() throws Exception {
        Player toUpdatePlayer = playerRepository.findAll().get(0);
        ConstrainedFields fields = new ConstrainedFields(PlayerDto.class);

        this.mockMvc.perform(put("/player/{id}", toUpdatePlayer.getId())
                .header(FieldRentConstants.AUTH_HEADER_NAME, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZW1haWwuY29tIiwiZXhwIjoxNDcxNzU0NTIxfQ.H_pN70_FDgKpG4wK8vLMVPANSuQAesNyLrgTaWaaoqc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMock.updatePlayer(toUpdatePlayer)))
                .andExpect(status().isNoContent())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("id").description(""),
                                fields.withPath("client").description("Just the client e-mail"),
                                fields.withPath("name").description(""),
                                fields.withPath("level").description("")
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
    public void deletePlayer() throws Exception {
        Long playerId = playerRepository.findAll().get(0).getId();
        this.mockMvc.perform(delete("/player/{id}", playerId).accept(MediaType.APPLICATION_JSON)
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
