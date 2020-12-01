package pl.edu.agh.to.drugstore.runner;

import pl.edu.agh.to.drugstore.consoleCRUD.ConsoleApp;
import pl.edu.agh.to.drugstore.consoleCRUD.Parser;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class Runner {

    public static void main(String... args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");
        EntityManager em = emf.createEntityManager();

        MedicationDAO medicationDAO = new MedicationDAO(em);
        PersonDAO personDAO = new PersonDAO(em);

        Parser parser = new Parser(new ConsoleApp(em));
        List<String> query = Arrays.asList(args);
        parser.parse(query);

        em.close();
    }
}
