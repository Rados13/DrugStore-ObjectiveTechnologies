package pl.edu.agh.to.drugstore.model.business;

import pl.edu.agh.to.drugstore.model.medications.Medication;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collection;

public class Magazine {

    private final EntityManager em;

    public Magazine(EntityManager em) {
        this.em = em;
    }

    public void addProduct(ConcreteProduct product) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(product);
        etx.commit();
    }

    public Collection<Medication> searchForMedicine() {
        // TODO
        return null;
    }

    public Collection<ConcreteProduct> searchForProduct() {
        // TODO
        return null;
    }


}
