package pl.edu.agh.to.drugstore.model.business;

import pl.edu.agh.to.drugstore.model.people.Address;
import pl.edu.agh.to.drugstore.model.people.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class HRDepartment {

    private final EntityManager em;

    public HRDepartment(EntityManager em) {
        this.em = em;
    }

    public void addPerson(Person person) {
        EntityTransaction etx = em.getTransaction();

        if(person.getAddress()!=null) {
            Address personAddress = searchExistingAddress(person.getAddress());
            if (personAddress == null) {
                int id = addAddress(person.getAddress());
                person.setAddress(em.find(Address.class, id));
            } else person.setAddress(personAddress);
        }
        etx.begin();
        em.persist(person);
        etx.commit();
    }

    public Collection<Notification> getNotifications(Person person) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();

        String sql = "from Notification where personId = :id";
        Query query = em.createQuery(sql);
        query.setParameter("id", person.getId());
        List result = query.getResultList();

        etx.commit();
        return result;
    }

    public List<Person> searchAllPersons() {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        Query query = em.createQuery("from Person");
        List result = query.getResultList();
        etx.commit();

        return result;
    }

    public List searchPersons(Person person){
        EntityTransaction etx = em.getTransaction();

        Address personAddress = searchExistingAddress(person.getAddress());
        etx.begin();
        String sql = "from Person where ";
        if(person.getRole()!=null)sql += " role = :role and";
        if(person.getFirstname()!=null)sql += " firstname = :firstname and";
        if(person.getLastname()!=null)sql += " lastname = :lastname and";
        if(person.getBirthdate()!=null)sql += " birthdate = :birthdate and";
        if(!person.getPESEL().equals("-"))sql += " PESEL = :pesel and";
        if(personAddress!=null)sql += " address = :address and";
        sql = new StringBuffer(sql).replace(sql.length()-3,sql.length(),"").toString();

        Query query = em.createQuery(sql);
        if(person.getRole()!=null)query.setParameter("role",person.getRole());
        if(person.getFirstname()!=null)query.setParameter("firstname",person.getFirstname());
        if(person.getLastname()!=null)query.setParameter("lastname",person.getLastname());
        if(person.getBirthdate()!=null)query.setParameter("birthdate",person.getBirthdate());
        if(!person.getPESEL().equals("-"))query.setParameter("PESEL",person.getPESEL());
        if(personAddress!=null)query.setParameter("address",person.getAddress());

        List result = query.getResultList();
        etx.commit();
        return result;
    }

    public void deletePerson(int personID){
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Person person = em.find(Person.class,personID);
        System.out.println(person.toString());
        em.remove(person);
        etx.commit();
    }

    public Address searchExistingAddress(Address address){
        List<Address> addresses = searchAddresses(address);
        if(addresses == null) return null;
        Optional<Address> possible = addresses.stream()
                .filter(address::equalWithoutID).findFirst();
        return possible.orElse(null);
    }

    public List<Address> searchAddresses(Address address){
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        String sql = "from Address where ";
        if(address.getCity()!=null)sql += " city = :city and";
        if(address.getStreet()!=null)sql += " street = :street and";
        if(address.getHouseId()!=null)sql += " houseId = :houseID and";
        if(address.getApartmentId()!=null)sql += " apartmentId = :apartmentID and";
        sql = new StringBuffer(sql).replace(sql.length()-3,sql.length(),"").toString();

        Query query = em.createQuery(sql);
        if(address.getCity()!=null)query.setParameter("city",address.getCity());
        if(address.getStreet()!=null)query.setParameter("street",address.getStreet());
        if(address.getHouseId()!=null)query.setParameter("houseID",address.getHouseId());
        if(address.getApartmentId()!=null)query.setParameter("apartmentID",address.getApartmentId());

        List result = query.getResultList();
        etx.commit();
        return result;
    }

    public int addAddress(Address address){
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(address);
        etx.commit();
        return address.getId();
    }

    public void deleteAddress(int addressID){
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Address address = em.find(Address.class,addressID);
        System.out.println(address.toString());
        em.remove(address);
        etx.commit();
    }
}