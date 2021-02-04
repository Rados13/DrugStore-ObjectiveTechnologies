package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.medications.Illness;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class IllnessDAO implements ObjectDAO<Illness> {

    private final EntityManager em;

    public IllnessDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Zwraca listę wszystkich zarejestrowanych chorób
     *
     * @return
     */
    @Override
    public List<Illness> findAll() {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        Query query = em.createQuery("from Illness");
        List result = query.getResultList();
        etx.commit();

        return result;
    }

    /**
     * Zwraca chorobę o podanym id
     *
     * @param id
     * @return
     */
    @Override
    public Illness find(int id) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Illness illness = em.find(Illness.class, id);
        etx.commit();

        return illness;
    }

    /**
     * Zapisuje w bazie nową chorobę ( :) )
     *
     * @param illness
     */
    @Override
    public void add(Illness illness) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(illness);
        etx.commit();
    }

    /**
     * Usuwa z bazy chorobę o podanym id
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Illness illness = em.find(Illness.class, id);
        em.remove(illness);
        etx.commit();
    }

    /**
     * Aktualizuje w bazie informacje o danej chorobie
     *
     * @param illness
     */
    @Override
    public void update(Illness illness) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.merge(illness);
        etx.commit();
    }
}
