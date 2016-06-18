package br.com.fieldrent.documentation;


import br.com.fieldrent.FieldrentApplication;
import br.com.fieldrent.dto.ReservationDto;
import br.com.fieldrent.mock.TestMock;
import br.com.fieldrent.model.Client;
import br.com.fieldrent.model.Company;
import br.com.fieldrent.model.Reservation;
import br.com.fieldrent.repository.ReservationRepository;
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
public class ReservationDocumentation {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    private RestDocumentationResultHandler document;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    TestMock testMock;

    @Before
    public void setUp() {
        testMock.createFields();
        testMock.createClients();
        testMock.createReservations();

        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @Test
    public void listReservationsByDay() throws Exception {

        this.mockMvc.perform(get("/reservations/{day}", "16-04-2016").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                                responseFields(
                                        fieldWithPath("[].id").description("Database id."),
                                        fieldWithPath("[].field").description("Just the field name."),
                                        fieldWithPath("[].client").description("Just the client e-mail"),
                                        fieldWithPath("[].date").description(""),
                                        fieldWithPath("[].startTime").description(""),
                                        fieldWithPath("[].endTime").description(""),
                                        fieldWithPath("[].reservationStatus").description("")
                                )
                        )
                )
                .andDo(this.document.snippets(
                                pathParameters(
                                        parameterWithName("day").description("The day and date with the format dd-MM-yyyy"))
                        )
                );
    }

    @Test
    public void listReservationsByDayAndCompany() throws Exception {

        Company company = reservationRepository.findAll().get(0).getField().getCompany();

        this.mockMvc.perform(get("/reservations/{day}/company/{cnpj}", "16-04-2016", company.getCnpj()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("[].id").description("Database id."),
                                fieldWithPath("[].field").description("Just the field name."),
                                fieldWithPath("[].client").description("Just the client e-mail"),
                                fieldWithPath("[].date").description(""),
                                fieldWithPath("[].startTime").description(""),
                                fieldWithPath("[].endTime").description(""),
                                fieldWithPath("[].reservationStatus").description("")
                        )
                        )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("day").description("The day and date with the format dd-MM-yyyy"),
                                parameterWithName("cnpj").description("The Company CNPJ.")
                        )
                        )
                );
    }

    @Test
    public void listReservationsByStatus() throws Exception {

        this.mockMvc.perform(get("/reservations/status/{status}", "OPEN").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("[].id").description("Database id."),
                                fieldWithPath("[].field").description("Just the field name."),
                                fieldWithPath("[].client").description("Just the client e-mail"),
                                fieldWithPath("[].date").description(""),
                                fieldWithPath("[].startTime").description(""),
                                fieldWithPath("[].endTime").description(""),
                                fieldWithPath("[].reservationStatus").description("")
                        )
                        )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("status").description("Status can be either: OPEN, CONFIRMED or CANCELED."))
                        )
                );
    }

    @Test
    public void listReservationsByCompany() throws Exception {

        Company company = reservationRepository.findAll().get(0).getField().getCompany();

        this.mockMvc.perform(get("/reservations/company/{cnpj}", company.getCnpj()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("[].id").description("Database id."),
                                fieldWithPath("[].field").description("Just the field name."),
                                fieldWithPath("[].client").description("Just the client e-mail"),
                                fieldWithPath("[].date").description(""),
                                fieldWithPath("[].startTime").description(""),
                                fieldWithPath("[].endTime").description(""),
                                fieldWithPath("[].reservationStatus").description("")
                        )
                        )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("cnpj").description("The company CNPJ."))
                        )
                );
    }

    @Test
    public void listReservationsByClient() throws Exception {
        Client client = reservationRepository.findAll().get(0).getClient();

        this.mockMvc.perform(get("/reservations/client/{email}", client.getEmail()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("[].id").description("Database id."),
                                fieldWithPath("[].field").description("Just the field name."),
                                fieldWithPath("[].client").description("Just the client e-mail"),
                                fieldWithPath("[].date").description(""),
                                fieldWithPath("[].startTime").description(""),
                                fieldWithPath("[].endTime").description(""),
                                fieldWithPath("[].reservationStatus").description("")
                        )
                        )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("email").description("The Client e-mail."))
                        )
                );
    }

    @Test
    public void listReservationsByStatusAndCompany() throws Exception {

        Company company = reservationRepository.findAll().get(0).getField().getCompany();

        this.mockMvc.perform(get("/reservations/status/{status}/company/{cnpj}", "OPEN", company.getCnpj()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("[].id").description("Database id."),
                                fieldWithPath("[].field").description("Just the field name."),
                                fieldWithPath("[].client").description("Just the client e-mail"),
                                fieldWithPath("[].date").description(""),
                                fieldWithPath("[].startTime").description(""),
                                fieldWithPath("[].endTime").description(""),
                                fieldWithPath("[].reservationStatus").description("")
                        )
                        )
                )
                .andDo(this.document.snippets(
                        pathParameters(
                                parameterWithName("status").description("Status can be either: OPEN, CONFIRMED or CANCELED."),
                                parameterWithName("cnpj").description("The company CNPJ.")
                        )
                        )
                );
    }

    @Test
    public void getReservation() throws Exception {

        Long reservationId = reservationRepository.findAll().get(0).getId();
        this.mockMvc.perform(get("/reservation/{id}", reservationId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document.snippets(
                        responseFields(
                                fieldWithPath("id").description("Database id."),
                                fieldWithPath("field").description("Just the field name."),
                                fieldWithPath("client").description("Just the client e-mail"),
                                fieldWithPath("date").description(""),
                                fieldWithPath("startTime").description(""),
                                fieldWithPath("endTime").description(""),
                                fieldWithPath("reservationStatus").description("")
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
    public void createReservation() throws Exception {
        //Reservation mock = testMock.createReservation();

        ConstrainedFields fields = new ConstrainedFields(ReservationDto.class);
        String tmock = testMock.createReservation();
        this.mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMock.createReservation()))
                .andExpect(status().isCreated())
                .andDo(this.document.snippets(
                                requestFields(
                                        fields.withPath("field").description("Just the field name."),
                                        fields.withPath("client").description("Just the client e-mail"),
                                        fields.withPath("date").description("Date with format dd-MM-yyyy"),
                                        fields.withPath("startTime").description("Time with format hh:mm"),
                                        fields.withPath("endTime").description("Time with format hh:mm"),
                                        fields.withPath("reservationStatus").description("Status can be either: OPEN, CONFIRMED or CANCELED.")
                                )
                        )
                );

    }

    @Test
    public void updateReservation() throws Exception {
        Reservation toUpdateReservation = reservationRepository.findAll().get(0);
        ConstrainedFields fields = new ConstrainedFields(ReservationDto.class);

        this.mockMvc.perform(put("/reservation/{id}", toUpdateReservation.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(testMock.updateReservation(toUpdateReservation)))
                .andExpect(status().isNoContent())
                .andDo(this.document.snippets(
                        requestFields(
                                fields.withPath("id").description(""),
                                fields.withPath("field").description("Just the field name."),
                                fields.withPath("client").description("Just the client e-mail"),
                                fields.withPath("date").description("Date with format dd-MM-yyyy"),
                                fields.withPath("startTime").description("Time with format hh:mm"),
                                fields.withPath("endTime").description("Time with format hh:mm"),
                                fields.withPath("reservationStatus").description("Status can be either: OPEN, CONFIRMED or CANCELED.")
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
    public void deleteReservation() throws Exception {
        Long reservationId = reservationRepository.findAll().get(0).getId();
        this.mockMvc.perform(delete("/reservation/{id}", reservationId).accept(MediaType.APPLICATION_JSON))
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
