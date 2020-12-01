package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.medications.Medication;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collection;

public class MedicationDAO {

    private final EntityManager em;

    public MedicationDAO(EntityManager em) {
        this.em = em;
    }

    public void addMedication(Medication medication, int quantity) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();

        // TODO
        //  check if medication exists, if not insert, if yes increase quantity

        etx.commit();
    }

    public Collection<Medication> searchForMedicine() {
        // TODO
        //  search by given criteria

        return null;
    }
}
