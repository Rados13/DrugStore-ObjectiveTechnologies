package pl.edu.agh.to.drugstore.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.consoleCRUD.ConsoleApp;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class InteractiveRunner {

    private static final Logger logger = LoggerFactory.getLogger(InteractiveRunner.class);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");
        EntityManager em = emf.createEntityManager();

        MedicationDAO medicationDAO = new MedicationDAO(em);
        PersonDAO personDAO = new PersonDAO(em);

        try {
            new ConsoleApp(em).start();
        } catch (IOException e) {
            logger.error("An error appeared during ConsoleApp initialization!" + e);
            System.exit(2);
        }

        em.close();
    }
}
