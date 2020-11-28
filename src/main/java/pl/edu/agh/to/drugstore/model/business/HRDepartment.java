package pl.edu.agh.to.drugstore.model.business;

import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class HRDepartment {

    private final EntityManager em;

    public HRDepartment(EntityManager em) {
        this.em = em;
    }

    public void addPerson(Person person) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(person);
        etx.commit();
    }
}
