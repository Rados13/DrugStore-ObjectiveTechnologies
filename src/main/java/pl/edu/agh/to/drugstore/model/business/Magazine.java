package pl.edu.agh.to.drugstore.model.business;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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

}
