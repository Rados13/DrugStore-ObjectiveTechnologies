package pl.edu.agh.to.drugstore;

import pl.edu.agh.to.drugstore.consoleCRUD.ConsoleApp;
import pl.edu.agh.to.drugstore.model.business.ConcreteProduct;
import pl.edu.agh.to.drugstore.model.business.HRDepartment;
import pl.edu.agh.to.drugstore.model.business.Magazine;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");
        EntityManager em = emf.createEntityManager();

        Magazine magazine = new Magazine(em);
        HRDepartment hrDepartment = new HRDepartment(em);

        magazine.addProduct(new ConcreteProduct());
        hrDepartment.addPerson(new Person());

        try {
            new ConsoleApp(em).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        em.close();
    }
}
