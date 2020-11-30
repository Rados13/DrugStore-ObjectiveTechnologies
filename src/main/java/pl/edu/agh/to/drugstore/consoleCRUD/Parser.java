package pl.edu.agh.to.drugstore.consoleCRUD;

import org.hsqldb.persist.Log;
import pl.edu.agh.to.drugstore.model.medications.Medication;
import pl.edu.agh.to.drugstore.model.medications.MedicationForm;
import pl.edu.agh.to.drugstore.model.people.*;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Parser {

    ConsoleApp app;

    public Parser(ConsoleApp app) {
        this.app = app;
    }


    Address getAddressFromParams(List<String> params) {
        if (params.size() == 0) return null;
        return Address.builder()
                .city(params.size() > 0 ? params.get(0) : null)
                .street(params.size() > 1 ? params.get(1) : null)
                .houseId(params.size() > 2 && !params.get(2).equals("-")
                        ? Integer.parseInt(params.get(2)) : null)
                .apartmentId(params.size() > 3 && !params.get(3).equals("-")
                        ? Integer.parseInt(params.get(3)) : null)
                .build();
    }


    private Person parseClient(List<String> params) throws ParseException {
        if (params.size() < 6) {
            System.out.println("Not proper number of params");
            return null;
        }
        Date date = !params.get(4).equals("-") ? new SimpleDateFormat("dd/MM/yyyy").parse(params.get(4)) : null;
        return Person
                .builder()
                .role(!params.get(1).equals("-")?
                        Role.valueOf(params.get(1).toUpperCase()):null)
                .firstname(!params.get(2).equals("-")?params.get(2):null)
                .lastname(!params.get(3).equals("-")?params.get(3):null)
                .birthdate(date)
                .PESEL(params.get(5))
                .address(getAddressFromParams(params.subList(6, params.size())))
                .build();
    }

    void parse(List<String> params) {
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
                        person = parseClient(params);
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
                address = getAddressFromParams(params.subList(2, params.size()));
                break;
            default:
                System.out.println("Not proper name of thing to change");
                return;
        }

        switch (operation) {
            case "add":
                if (person != null) app.hrDepartment.addPerson(person);
                if (address != null) app.hrDepartment.addAddress(address);
                break;
            case "search":
                if (person != null)
                    app.hrDepartment.searchPersons(person)
                            .forEach(elem -> System.out.println(elem.toString()));
                if (address != null)
                    app.hrDepartment.searchAddresses(address)
                            .forEach(elem -> System.out.println(elem.toString()));
                break;
            case "delete":
                if (Arrays.stream(Role.values()).anyMatch(elem -> elem.toString()
                        .equals(params.get(1).toUpperCase()))) {
                    app.hrDepartment.deletePerson(Integer.parseInt(params.get(2)));
                }
                if (params.get(1).equals("address"))
                    app.hrDepartment.deleteAddress(Integer.parseInt(params.get(2)));
                break;
        }

    }

}
