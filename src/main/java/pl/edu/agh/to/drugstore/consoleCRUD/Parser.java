package pl.edu.agh.to.drugstore.consoleCRUD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.medications.MedicationForm;
import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;
import pl.edu.agh.to.drugstore.model.people.Role;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    private static final Logger logger = LoggerFactory.getLogger(Parser.class);
    private final ConsoleApp app;

    public Parser(ConsoleApp app) {
        this.app = app;
    }

    public void parse(List<String> params) {
        String operation = params.get(0);
        Person person = null;
        Medication medication = null;
        Address address = null;
        switch (params.get(1)) {
            case "client":
                // optional permissions here
            case "seller":
                // optional permissions here
            case "administrator":
                if (!params.get(0).equals("delete")) {
                    try {
                        person = Person.personBuilder(params);
                    } catch (ParseException e) {
                        logger.info("A problem appeared when parsing Person object" + e);
                    }
                }
                break;
            case "medication":
                medication = Medication.builder()
                        .name(params.get(2))
                        .price(BigDecimal.valueOf(Double.parseDouble(params.get(3))))
                        .prescriptionRequired(Boolean.parseBoolean(params.get(4)))
                        .form(MedicationForm.valueOf(params.get(5)))
                        .build();
                break;
            case "address":
                address = Address.addressBuilder(params.subList(2, params.size()));
                break;
            case "people":
                System.out.println("Showing all people from database:");
                break;
            default:
                logger.info("Not proper name of thing to change");
                return;
        }

        switch (operation) {
            case "add":
                if (person != null) app.getPersonDAO().add(person);
                if (address != null) app.getAddressDAO().add(address);
                break;
            case "search":
                if (person != null)
                    app.getPersonDAO().searchPeople(person)
                            .forEach(elem -> System.out.println(elem.toString()));
                if (address != null)
                    app.getAddressDAO().searchAddresses(address)
                            .forEach(elem -> System.out.println(elem.toString()));
                break;
            case "delete":
                if (Arrays.stream(Role.values()).anyMatch(elem -> elem.toString()
                        .equals(params.get(1).toUpperCase()))) {
                    app.getPersonDAO().delete(Integer.parseInt(params.get(2)));
                }
                if (params.get(1).equals("address"))
                    app.getAddressDAO().delete(Integer.parseInt(params.get(2)));
                break;
            case "show":
                List<Person> allExisting = new ArrayList<>(app.getPersonDAO().findAll());
                logger.info("Found " + allExisting.size() + " people:");
                allExisting.forEach(p -> logger.info(String.valueOf(p)));
        }
    }
}