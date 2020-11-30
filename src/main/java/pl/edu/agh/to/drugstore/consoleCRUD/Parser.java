package pl.edu.agh.to.drugstore.consoleCRUD;

import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.medications.MedicationForm;
import pl.edu.agh.to.drugstore.model.people.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

public class Parser {

    ConsoleApp app;

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
            case "seller":
            case "administrator":
                if (!params.get(0).equals("delete")) {
                    try {
                        person = Person.personBulder(params);
                    } catch (ParseException e) {
                        e.printStackTrace();
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
            default:
                System.out.println("Not proper name of thing to change");
                return;
        }

        switch (operation) {
            case "add":
                if (person != null) app.getHrDepartment().addPerson(person);
                if (address != null) app.getHrDepartment().addAddress(address);
                break;
            case "search":
                if (person != null)
                    app.getHrDepartment().searchPersons(person)
                            .forEach(elem -> System.out.println(elem.toString()));
                if (address != null)
                    app.getHrDepartment().searchAddresses(address)
                            .forEach(elem -> System.out.println(elem.toString()));
                break;
            case "delete":
                if (Arrays.stream(Role.values()).anyMatch(elem -> elem.toString()
                        .equals(params.get(1).toUpperCase()))) {
                    app.getHrDepartment().deletePerson(Integer.parseInt(params.get(2)));
                }
                if (params.get(1).equals("address"))
                    app.getHrDepartment().deleteAddress(Integer.parseInt(params.get(2)));
                break;
        }
    }
}