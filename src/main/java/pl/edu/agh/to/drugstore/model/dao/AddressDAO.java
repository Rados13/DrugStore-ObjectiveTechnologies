package pl.edu.agh.to.drugstore.model.dao;

import pl.edu.agh.to.drugstore.model.people.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class AddressDAO implements ObjectDAO<Address> {

    private final EntityManager em;

    public AddressDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Address> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Address find(int id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Dodaje do bazy danych nowy adres.
     *
     * @param address
     * @return
     */
    @Override
    public void add(Address address) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.persist(address);
        etx.commit();
    }

    /**
     * Usuwa z bazy danych adres o podanym ID.
     *
     * @param addressID
     */
    @Override
    public void delete(int addressID) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        Address address = em.find(Address.class, addressID);
        System.out.println(address.toString());
        em.remove(address);
        etx.commit();
    }

    /**
     * Aktualizuje w bazie danych informacje o adresie
     *
     * @param address
     */
    @Override
    public void update(Address address) {
        EntityTransaction etx = em.getTransaction();
        etx.begin();
        em.merge(address);
        etx.commit();
    }

    /**
     * Wyszukuje w bazie danych adres o określonych parametrach.
     *
     * @param address
     * @return
     */
    public Address searchExistingAddress(Address address) {
        List<Address> addresses = searchAddresses(address);
        if (addresses == null) return null;
        Optional<Address> possible = addresses.stream()
                .filter(address::equalWithoutID).findFirst();
        return possible.orElse(null);
    }

    /**
     * Wyszukuje w bazie danych adres o określonych parametrach.
     *
     * @param address
     * @return
     */
    public List<Address> searchAddresses(Address address) {
        EntityTransaction etx = em.getTransaction();

        etx.begin();
        String sql = "from Address where ";
        if (address.getCity() != null) sql += " city = :city and";
        if (address.getStreet() != null) sql += " street = :street and";
        if (address.getHouseId() != null) sql += " houseId = :houseID and";
        if (address.getApartmentId() != null) sql += " apartmentId = :apartmentID and";
        sql = new StringBuffer(sql).replace(sql.length() - 3, sql.length(), "").toString();

        Query query = em.createQuery(sql);
        if (address.getCity() != null) query.setParameter("city", address.getCity());
        if (address.getStreet() != null) query.setParameter("street", address.getStreet());
        if (address.getHouseId() != null) query.setParameter("houseID", address.getHouseId());
        if (address.getApartmentId() != null) query.setParameter("apartmentID", address.getApartmentId());

        List result = query.getResultList();
        etx.commit();
        return result;
    }

}
