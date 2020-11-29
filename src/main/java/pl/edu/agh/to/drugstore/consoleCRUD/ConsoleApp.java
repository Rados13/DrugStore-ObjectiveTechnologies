package pl.edu.agh.to.drugstore.consoleCRUD;

import pl.edu.agh.to.drugstore.model.business.HRDepartment;
import pl.edu.agh.to.drugstore.model.business.Magazine;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ConsoleApp {

    final Magazine magazine;
    final HRDepartment hrDepartment;

    public ConsoleApp(EntityManager em) {
        this.magazine = new Magazine(em);
        this.hrDepartment = new HRDepartment(em);
    }

    public void start() throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        Parser parser = new Parser(this);

        System.out.println("Write command or q to quit or h for help");
        String input = reader.readLine();
        ArrayList<String> params = new ArrayList<>();
        while (!input.equals("q")) {
            params = Arrays.stream(input.split(" ")).collect(Collectors.toCollection(ArrayList::new));
            if (params.size() == 1 && params.get(0).equals("h")) printHelp();
            else if (params.size() <= 2) System.out.println("Not proper number of params");
            else parser.parse(params);
            System.out.println("Write new command");
            input = reader.readLine();
        }
    }


    private void printHelp(){
        System.out.println("For now possible are three actions:");
        System.out.println("add)    adding new element");
        System.out.println("search) print all elements from db which match criteria");
        System.out.println("delete) delete specific element on this id from db");
        System.out.println("Each action can by applied on Person or Address");
        System.out.println("Person has fields: role, firstname, lastname, birthdate (format: dd/mm/yyyy), Address");
        System.out.println("Possible roles are client, administrator, seller");
        System.out.println("Address has fields: city, street, houseId, apartmentId");
        System.out.println("If you want to pass one of the field instead of value click -");
        System.out.println("Example commands:");
        System.out.println("add client Jan Wozniak 11/01/1971 710111543216 Krakow");
        System.out.println("search administrator John - - - - - - - - - - -");
        System.out.println("This line search all administrators with name John");



    }
}
