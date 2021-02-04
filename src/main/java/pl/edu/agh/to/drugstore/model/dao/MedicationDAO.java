package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.medications.Medication;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class MedicationDAO implements ObjectDAO<Medication> {

    private final EntityManager em;

    public MedicationDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Medication> findAll() {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        Query query = em.createQuery("from Medication");
        List<Medication> result = query.getResultList();
        etx.commit();

        return result;
    }

    @Override
    public Medication find(int id) {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        Medication medication = em.find(Medication.class, id);
        etx.commit();

        return medication;
    }

    @Override
    public void add(Medication medication) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();

        Query query = em.createQuery("from Medication as m where m.name = :medication_name");
        query.setParameter("medication_name", Medication.formatMedicationName(medication.getName()));
        List<Medication> result = query.getResultList();

        if (result.size() != 0) {
            int quantityToAdd = medication.getQuantity();
            medication = result.get(0);
            medication.setQuantity(medication.getQuantity() + quantityToAdd);
            em.merge(medication);
        } else {
            em.persist(medication);
        }
        etx.commit();
    }

    public int getMedicationIndexViaName(List<Medication> medications, String name) {
        if (medications.stream().map(Medication::getName).anyMatch(medicationName -> medicationName.equals(name))) {
            return medications.stream().map(Medication::getName).collect(Collectors.toList()).indexOf(name);
        }
        return -1;
    }

    @Override
    public void update(Medication medication) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.merge(medication);
        etx.commit();
    }

    @Override
    public void delete(int id) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Medication medication = em.find(Medication.class, id);
        em.remove(medication);
        etx.commit();
    }
}