package pl.edu.agh.to.drugstore;

import pl.edu.agh.to.drugstore.model.business.ConcreteProduct;
import pl.edu.agh.to.drugstore.model.business.HRDepartment;
import pl.edu.agh.to.drugstore.model.business.Magazine;
import pl.edu.agh.to.drugstore.model.people.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");
        EntityManager em = emf.createEntityManager();

        Magazine magazine = new Magazine(em);
        HRDepartment hrDepartment = new HRDepartment(em);

        magazine.addProduct(new ConcreteProduct());
        hrDepartment.addPerson(new Client());

        em.close();
    }
}
