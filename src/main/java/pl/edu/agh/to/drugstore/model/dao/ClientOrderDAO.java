package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.people.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ClientOrderDAO implements ObjectDAO<ClientOrder> {

    private final EntityManager em;

    public ClientOrderDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Zwraca listę wszystkich zamówień zapisanych w bazie danych.
     * @return
     */
    @Override
    public List<ClientOrder> findAll() {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        Query query = em.createQuery("from ClientOrder");
        List result = query.getResultList();
        etx.commit();

        return result;
    }

    /**
     * Zwraca obiekt o podanym numerze ID
     * @param orderID
     * @return
     */
    @Override
    public ClientOrder find(int orderID) {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        ClientOrder order = em.find(ClientOrder.class, orderID);
        etx.commit();

        return order;
    }

    /**
     * Dodaje nowe zamówienie do bazy danych.
     * @param order
     */
    @Override
    public void add(ClientOrder order) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(order);
        etx.commit();
    }

    /**
     * Usuwa zamówienie z bazy danych
     * @param orderID
     */
    @Override
    public void delete(int orderID){
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        ClientOrder order = em.find(ClientOrder.class, orderID);
        System.out.println(order.toString());
        em.remove(order);
        etx.commit();
    }

    /**
     * Aktualizuje w bazie danych dane dotyczące określonego zamówienia.
     * @param newOrder
     */
    @Override
    public void update(ClientOrder newOrder){
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.merge(newOrder);
        etx.commit();
    }

}
