package pl.edu.agh.to.drugstore.model.business;

import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

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

    public Collection<Notification> getNotifications(Person person) {
        // TODO
        EntityTransaction etx = em.getTransaction();
        etx.begin();

        String sql = "from Notification where personId = :id";
        Query query = em.createQuery(sql);
        query.setParameter("id", person.getId());
        List result = query.getResultList();

        etx.commit();
        return result;
    }
}
