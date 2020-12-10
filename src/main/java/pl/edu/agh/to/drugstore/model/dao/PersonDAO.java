package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class PersonDAO {

    private final EntityManager em;

    private final AddressDAO addressDAO;

    public PersonDAO(EntityManager em) {
        this.em = em;
        this.addressDAO = new AddressDAO(em);
    }

    /**
     * Dodaje nową osobę do bazy danych.
     * @param person
     */
    public void addPerson(Person person) {
        EntityTransaction etx = em.getTransaction();

        if(person.getAddress()!=null) {
            Address personAddress = this.addressDAO.searchExistingAddress(person.getAddress());
            if (personAddress == null) {
                int id = this.addressDAO.addAddress(person.getAddress());
                person.setAddress(em.find(Address.class, id));
            } else person.setAddress(personAddress);
        }
        etx.begin();
        em.persist(person);
        etx.commit();
    }

    /**
     * Zwraca listę wszystkich osób zapisanych w bazie danych.
     * @return
     */
    public List<Person> searchAllPersons() {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        Query query = em.createQuery("from Person");
        List result = query.getResultList();
        etx.commit();

        return result;
    }

    /**
     * Zwraca listę osób spełniających określone kryteria.
     * @param person
     * @return
     */
    public List<Person> searchPeople(Person person){
        EntityTransaction etx = em.getTransaction();

        Address personAddress = person.getAddress()!=null?this.addressDAO.searchExistingAddress(person.getAddress()):null;
        etx.begin();
        String sql = "from Person where ";
        if(person.getRole()!=null)sql += " role = :role and";
        if(person.getFirstname()!=null)sql += " firstname = :firstname and";
        if(person.getLastname()!=null)sql += " lastname = :lastname and";
        if(person.getBirthdate()!=null)sql += " birthdate = :birthdate and";
        if(person.getPESEL()!=null)sql += " PESEL = :pesel and";
        if(personAddress!=null)sql += " address = :address and";
        sql = new StringBuffer(sql).replace(sql.length()-3,sql.length(),"").toString();

        Query query = em.createQuery(sql);
        if(person.getRole()!=null)query.setParameter("role",person.getRole());
        if(person.getFirstname()!=null)query.setParameter("firstname",person.getFirstname());
        if(person.getLastname()!=null)query.setParameter("lastname",person.getLastname());
        if(person.getBirthdate()!=null)query.setParameter("birthdate",person.getBirthdate());
        if(person.getPESEL()!=null)query.setParameter("PESEL",person.getPESEL());
        if(personAddress!=null)query.setParameter("address",person.getAddress());

        List result = query.getResultList();
        etx.commit();
        return result;
    }

    /**
     * Usuwa osobę z bazy danych
     * @param personID
     */
    public void deletePerson(int personID){
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Person person = em.find(Person.class,personID);
        System.out.println(person.toString());
        em.remove(person);
        etx.commit();
    }

    /**
     * Aktualizuje w bazie danych dane dotyczące określonej osoby.
     * @param newPerson
     */
    public void editPerson(Person newPerson){
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.merge(newPerson);
        etx.commit();
    }
}