package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class PersonDAO implements ObjectDAO<Person> {

    private final EntityManager em;

    private final AddressDAO addressDAO;

    public PersonDAO(EntityManager em) {
        this.em = em;
        this.addressDAO = new AddressDAO(em);
    }

    /**
     * Zwraca listę wszystkich osób zapisanych w bazie danych.
     *
     * @return
     */
    @Override
    public List<Person> findAll() {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        Query query = em.createQuery("from Person");
        List result = query.getResultList();
        etx.commit();

        return result;
    }

    /**
     * Zwraca z bazu osobę o podanym id
     *
     * @param id
     * @return
     */
    @Override
    public Person find(int id) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Person person = em.find(Person.class, id);
        etx.commit();

        return person;
    }

    /**
     * Dodaje nową osobę do bazy danych.
     *
     * @param person
     */
    @Override
    public void add(Person person) {
        EntityTransaction etx = em.getTransaction();

        if (person.getAddress() != null) {
            Address personAddress = this.addressDAO.searchExistingAddress(person.getAddress());
            if (personAddress == null) {
                this.addressDAO.add(person.getAddress());
                int id = person.getAddress().getId();
                person.setAddress(em.find(Address.class, id));
            } else person.setAddress(personAddress);
        }
        etx.begin();
        em.persist(person);
        etx.commit();
    }


    /**
     * Usuwa osobę z bazy danych
     *
     * @param personID
     */
    @Override
    public void delete(int personID) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Person person = em.find(Person.class, personID);
        System.out.println(person.toString());
        em.remove(person);
        etx.commit();
    }

    /**
     * Aktualizuje w bazie danych dane dotyczące określonej osoby.
     *
     * @param newPerson
     */
    @Override
    public void update(Person newPerson) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.merge(newPerson);
        etx.commit();
    }


    /**
     * Zwraca listę osób spełniających określone kryteria.
     *
     * @param person
     * @return
     */
    public List<Person> searchPeople(Person person) {
        EntityTransaction etx = em.getTransaction();

        Address personAddress = person.getAddress() != null ? this.addressDAO.searchExistingAddress(person.getAddress()) : null;
        etx.begin();
        String sql = "from Person where ";
        if (person.getRole() != null) sql += " role = :role and";
        if (person.getFirstname() != null) sql += " firstname = :firstname and";
        if (person.getLastname() != null) sql += " lastname = :lastname and";
        if (person.getBirthdate() != null) sql += " birthdate = :birthdate and";
        if (person.getPESEL() != null) sql += " PESEL = :pesel and";
        if (personAddress != null) sql += " address = :address and";
        sql = new StringBuffer(sql).replace(sql.length() - 3, sql.length(), "").toString();

        Query query = em.createQuery(sql);
        if (person.getRole() != null) query.setParameter("role", person.getRole());
        if (person.getFirstname() != null) query.setParameter("firstname", person.getFirstname());
        if (person.getLastname() != null) query.setParameter("lastname", person.getLastname());
        if (person.getBirthdate() != null) query.setParameter("birthdate", person.getBirthdate());
        if (person.getPESEL() != null) query.setParameter("PESEL", person.getPESEL());
        if (personAddress != null) query.setParameter("address", person.getAddress());

        List result = query.getResultList();
        etx.commit();
        return result;
    }

    /**
     * Usuwa osobę z bazy danych
     *
     * @param personID
     */
    public void deletePerson(int personID) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Person person = em.find(Person.class, personID);
        em.remove(person);
        etx.commit();
    }

    /**
     * Aktualizuje w bazie danych dane dotyczące określonej osoby.
     *
     * @param newPerson
     */
    public void editPerson(Person newPerson) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.merge(newPerson);
        etx.commit();
    }

    public Person findByLogin(String login) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        String sql = "from Person where";
        sql += " login = :login";

        Query query = em.createQuery(sql);
        query.setParameter("login", login);
        List result = query.getResultList();
        etx.commit();

        if (result.size() > 1) throw new RuntimeException("Login atribute is unique for each supplier.");
        return result.size() == 0 ? null : (Person) result.get(0);
    }
}