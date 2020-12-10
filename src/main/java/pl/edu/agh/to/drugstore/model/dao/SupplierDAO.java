package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.business.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class SupplierDAO implements ObjectDAO<Supplier> {

    private final EntityManager em;

    public SupplierDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Supplier> findAll() {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        Query query = em.createQuery("from Supplier");
        List result = query.getResultList();
        etx.commit();

        return result;
    }

    @Override
    public Supplier find(int id) {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        StringBuilder builder = new StringBuilder();
        builder.append("from Supplier where ");
        builder.append("id = ").append(id);

        Query query = em.createQuery(builder.toString());
        List result = query.getResultList();
        etx.commit();

        if (result.size() > 1) throw new RuntimeException("Id atribute is unique for each supplier.");
        return result.size() == 0 ? null : (Supplier) result.get(0);

    }

    @Override
    public void add(Supplier object) {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        em.persist(object);
        etx.commit();
    }

    @Override
    public void delete(int id) {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        Supplier supplier = em.find(Supplier.class, id);
        em.remove(supplier);
        etx.commit();
    }

    @Override
    public void update(Supplier object) {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        em.merge(object);
        etx.commit();
    }
}
