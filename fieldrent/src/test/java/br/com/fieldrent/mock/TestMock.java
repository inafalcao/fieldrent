package br.com.fieldrent.mock;

import br.com.fieldrent.dto.ClientCompanyDto;
import br.com.fieldrent.dto.PlayerDto;
import br.com.fieldrent.dto.ReservationDto;
import br.com.fieldrent.model.*;
import br.com.fieldrent.repository.*;
import br.com.fieldrent.security.UserRole;
import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inafalcao on 3/29/16.
 */
@Component
public class TestMock {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientCompanyRepository clientCompanyRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public TestMock() {

    }

    public void createClients() {
        Client c1 = new Client("Client1", "field@123!", "client@email1.com", "99999999", false,
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf", true);
        c1.generatePhotoLobFromBase64Photo();
        c1.grantRole(UserRole.USER);

        Client c2 = new Client("Client2", "passwd2", "client2@email.com", "00000000", false,
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf", false);
        c2.generatePhotoLobFromBase64Photo();

        clientRepository.save(c1);
        clientRepository.save(c2);
    }

    public void createCompanies() {
        Company c1 = new Company("ArcloSociety", "2983492472", "Rua Vicente Castro Filho, 1750", "(85) 42938274",
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf");

        Company c2 = new Company("Arena", "3983492472", "Rua Vicente Castro Filho, 1750", "(85) 42938274",
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf");

        companyRepository.save(c1);
        companyRepository.save(c2);
    }

  /*  public void createSchedules() {
        Schedule s1 = new Schedule(new LocalTime(), new LocalTime(), ScheduleStatus.AVAILABLE);
        Schedule s2 = new Schedule(new LocalTime(), new LocalTime(), ScheduleStatus.UNAVAILABLE);

    }*/

    public Company createCompany() {
        Company c1 = new Company("No Alvo", "4983492472", "Rua Vicente Castro Filho, 1750", "(85) 42938274",
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf");
        return c1;
    }

    public void createFields() {
        createCompanies();

        Company company = companyRepository.findAll().get(0);
        Schedule s1 = new Schedule(new LocalTime(12, 0), new LocalTime(13, 0), ScheduleStatus.AVAILABLE);
        Schedule s2 = new Schedule(new LocalTime(14, 0), new LocalTime(15, 0), ScheduleStatus.UNAVAILABLE);
        scheduleRepository.save(s1);
        scheduleRepository.save(s2);

        List<Schedule> schedules = new ArrayList<Schedule>(2);
        schedules.add(s1);
        schedules.add(s2);

        Field f1 = new Field("Campo 1", "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf", company, schedules);
        Field f2 = new Field("Campo 2", "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf", company);

        fieldRepository.save(f1);
        fieldRepository.save(f2);
    }

    public String createField() {
        Company company = companyRepository.findAll().get(0);

        List<Schedule> schedule = new ArrayList<>(2);

        Field f1 = new Field("Campo 66", "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf", company, schedule);

        String field = new Gson().toJson(f1);
        String schedules = "[ {\n" +
                "    \"startTime\" : \"12:00\",\n" +
                "    \"endTime\" : \"13:00\",\n" +
                "    \"status\" : \"AVAILABLE\"\n" +
                "  }, {\n" +
                "    \"startTime\" : \"14:00\",\n" +
                "    \"endTime\" : \"15:00\",\n" +
                "    \"status\" : \"UNAVAILABLE\"\n" +
                "  } ]";

        String withSchedule = field.replace("[]", schedules);

        return withSchedule;
    }

    public String updateField(Field toUpdate) {
        Field toUpdateField = toUpdate;
        toUpdateField.setName("New name");
        toUpdateField.generateBase64PhotoFromPhotoLob();
        toUpdateField.setPhotoLob(null);
        toUpdateField.getSchedules().clear();

        String field = new Gson().toJson(toUpdateField);
        String schedules = "[ {\n" +
                //"    \"id\" : 77,\n" +
                "    \"startTime\" : \"12:00\",\n" +
                "    \"endTime\" : \"13:00\",\n" +
                "    \"status\" : \"AVAILABLE\"\n" +
                "  }, {\n" +
                //"    \"id\" : 79,\n" +
                "    \"startTime\" : \"14:00\",\n" +
                "    \"endTime\" : \"15:00\",\n" +
                "    \"status\" : \"UNAVAILABLE\"\n" +
                "  } ]";
        String withSchedule = field.replace("[]", schedules);
        return field;
    }

    public void createReservations() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        Field field = fieldRepository.findAll().get(0);
        Client client = clientRepository.findAll().get(0);

        DateTime dt1 = formatter.parseDateTime("16/04/2016");
        Reservation r1 = new Reservation(client, field, dt1, new LocalTime(12, 0), new LocalTime(13, 0), ReservationStatus.OPEN);

        DateTime dt2 = formatter.parseDateTime("17/04/2016");
        Reservation r2 = new Reservation(client, field, dt1, new LocalTime(14, 0), new LocalTime(15, 0), ReservationStatus.OPEN);

        reservationRepository.save(r1);
        reservationRepository.save(r2);
    }

    public String createReservation() {
        Client client = clientRepository.findAll().get(0);

        ReservationDto res = new ReservationDto(client.getEmail(), "Campo 1", "18-04-2016", "12:00", "13:00",  ReservationStatus.OPEN);
        String reservationString = new Gson().toJson(res);

        return reservationString;
    }

    public String updateReservation(Reservation toUpdateReservation) {

        ReservationDto res = new ReservationDto(toUpdateReservation.getClient().getEmail(),
                                                toUpdateReservation.getField().getName(),
                                                "18-04-2016",
                                                "12:00",
                                                "13:00",
                                                ReservationStatus.CANCELED);
        res.setId(toUpdateReservation.getId());
        String reservationString = new Gson().toJson(res);

        return reservationString;
    }

    public void createClientsCompany() {
        Client client = clientRepository.findAll().get(0);
        Company company = companyRepository.findAll().get(0);

        ClientCompany clientCompany = new ClientCompany(client, company, true);
        clientCompanyRepository.save(clientCompany);
    }

    public String createClientCompany() {
        Client client = clientRepository.findAll().get(0);
        Company company = companyRepository.findAll().get(0);

        ClientCompanyDto res = new ClientCompanyDto(client.getEmail(), company.getCnpj(), true);
        String clientCompanyString = new Gson().toJson(res);

        return clientCompanyString;
    }

    public String updateClientCompany(ClientCompany toUpdateClientCompany) {

        ClientCompanyDto ccd = new ClientCompanyDto(toUpdateClientCompany.getClient().getEmail(),
                                                    toUpdateClientCompany.getCompany().getCnpj(),
                                                    !toUpdateClientCompany.getIsAdmin());
        ccd.setId(toUpdateClientCompany.getId());
        String clientCompanyString = new Gson().toJson(ccd);
        return clientCompanyString;
    }

    public void createPlayers() {
        Client client = clientRepository.findAll().get(0);

        Player player = new Player("Player 1", 5, client);
        playerRepository.save(player);
    }

    public String createPlayer() {
        Client client = clientRepository.findAll().get(0);

        PlayerDto res = new PlayerDto(client.getEmail(), "Player J", 4);
        String playerString = new Gson().toJson(res);

        return playerString;
    }

    public String updatePlayer(Player toUpdatePlayer) {

        PlayerDto pdto = new PlayerDto(toUpdatePlayer.getClient().getEmail(),
                                      "New Player", 3);
        pdto.setId(toUpdatePlayer.getId());
        String playerString = new Gson().toJson(pdto);
        return playerString;
    }

}
