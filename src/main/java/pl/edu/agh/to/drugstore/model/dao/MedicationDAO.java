package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.medications.Medication;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class MedicationDAO implements ObjectDAO<Medication> {

    private final EntityManager em;

    public MedicationDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Zwraca listę wszystkich lekarstw zapisanych w bazie danych
     * @return
     */
    @Override
    public List<Medication> findAll() {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        Query query = em.createQuery("from Medication");
        List result = query.getResultList();
        etx.commit();

        return result;
    }

    /**
     * Zwraca lekarstwo o podanym ID
     * @param id
     * @return
     */
    @Override
    public Medication find(int id) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Medication medication = em.find(Medication.class, id);
        etx.commit();

        return medication;
    }

    /**
     * Dodaje nowe lekarstwo do bazy danych
     * @param medication
     */
    @Override
    public void add(Medication medication) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(medication);
        etx.commit();
    }

    /**
     * Usuwa z bazy lekarstwo o danym numerze ID
     * @param id
     */
    @Override
    public void delete(int id) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Medication medication = em.find(Medication.class, id);
        System.out.println(medication.toString());
        em.remove(medication);
        etx.commit();
    }

    /**
     * Uaktualnia dane o danym lekarstwie w bazie danych
     * @param medication
     */
    @Override
    public void update(Medication medication) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.merge(medication);
        etx.commit();
    }
}
