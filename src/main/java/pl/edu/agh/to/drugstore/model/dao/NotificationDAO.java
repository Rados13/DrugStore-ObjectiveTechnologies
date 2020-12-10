package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.business.Notification;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

public class NotificationDAO implements ObjectDAO<Notification> {

    private final EntityManager em;

    public NotificationDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Notification> findAll() {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Query query = em.createQuery("from Notification ");
        List result = query.getResultList();
        etx.commit();
        return result;
    }

    @Override
    public Notification find(int id) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Notification result = em.find(Notification.class, id);
        etx.commit();
        return result;
    }

    @Override
    public void add(Notification notification) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(notification);
        etx.commit();
    }

    @Override
    public void delete(int id) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Notification notification = em.find(Notification.class, id);
        em.remove(notification);
        etx.commit();
    }

    @Override
    public void update(Notification notification) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.merge(notification);
        etx.commit();
    }

    /**
     * Wyszukuje w bazie danych i zwraca kolekcję powiadomień dotyczących konkretnej osoby.
     * @param person
     * @return
     */
    public List<Notification> findAllByPerson(Person person) {
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
