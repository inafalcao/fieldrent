package br.com.fieldrent.documentation;


import br.com.fieldrent.FieldrentApplication;
import br.com.fieldrent.mock.TestMock;
import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.Field;
import br.com.fieldrent.model.Field;
import br.com.fieldrent.repository.FieldRepository;
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

import java.util.List;

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
public class FieldDocumentation {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    TestMock testMock;

    @Before
    public void setUp() {
        testMock.createFields();

        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @Test
    public void listFields() throws Exception {

        this.mockMvc.perform(get("/fields").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("[].id").description("Database id."),
                                fieldWithPath("[].name").description(""),
                                fieldWithPath("[].photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo"),
                                fieldWithPath("[].company").description(""),
                                fieldWithPath("[].schedules").description("")
                        )
                        )
                );

    }

    @Test
    public void getField() throws Exception {

        Long fieldId = fieldRepository.findAll().get(0).getId();
        this.mockMvc.perform(get("/field/{id}", fieldId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("id").description("Database id."),
                                fieldWithPath("name").description(""),
                                fieldWithPath("photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo"),
                                fieldWithPath("company").description(""),
                                fieldWithPath("schedules").description("")
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
    public void createField() throws Exception {
        ConstrainedFields fields = new ConstrainedFields(Field.class);

        this.mockMvc.perform(post("/field")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMock.createField()))
                .andExpect(status().isCreated())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("name").description(""),
                                fields.withPath("photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo"),
                                fields.withPath("company").description(""),
                                fields.withPath("schedules").description("")
                        )
                        )
                );
    }

    /*@Test
    public void updateField() throws Exception {
        Field toUpdateField = fieldRepository.findAll().get(0);

        ConstrainedFields fields = new ConstrainedFields(Field.class);

        this.mockMvc.perform(put("/field/{id}", toUpdateField.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(testMock.updateField(toUpdateField))))
                .andExpect(status().isNoContent())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("id").description(""),
                                fields.withPath("name").description(""),
                                fields.withPath("photo")
                                        .type(JsonFieldType.STRING)
                                        .description("Base64 encoded photo"),
                                fields.withPath("company").description(""),
                                fields.withPath("schedules").description("")
                        )
                        )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("id").description("The database entity id"))
                        )
                );
    }*/

    @Test
    public void deleteField() throws Exception {
        Long fieldId = fieldRepository.findAll().get(0).getId();
        this.mockMvc.perform(delete("/field/{id}", fieldId).accept(MediaType.APPLICATION_JSON))
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
