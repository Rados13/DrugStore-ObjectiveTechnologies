package pl.edu.agh.to.drugstore.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.consoleCRUD.ConsoleApp;
import pl.edu.agh.to.drugstore.model.business.HRDepartment;
import pl.edu.agh.to.drugstore.model.business.Magazine;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.logging.Level;

public class InteractiveRunner {

    private static final Logger logger = LoggerFactory.getLogger(InteractiveRunner.class);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");
        EntityManager em = emf.createEntityManager();

        Magazine magazine = new Magazine(em);
        HRDepartment hrDepartment = new HRDepartment(em);

        try {
            new ConsoleApp(em).start();
        } catch (IOException e) {
            logger.error("An error appeared during ConsoleApp initialization!" + e);
            System.exit(2);
        }

        em.close();
    }
}
