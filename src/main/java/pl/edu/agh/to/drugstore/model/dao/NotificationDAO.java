package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.Notification;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

public class NotificationDAO {

    private final EntityManager em;

    public NotificationDAO(EntityManager em) {
        this.em = em;
    }

    public Collection<Notification> getNotifications(Person person) {
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
