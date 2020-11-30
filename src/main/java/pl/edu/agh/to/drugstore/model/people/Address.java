package pl.edu.agh.to.drugstore.model.people;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
public class Address {

    public Address() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String city;
    private String street;
    private Integer houseId;
    private Integer apartmentId;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        if (houseId <= 0) throw new IllegalArgumentException("House Id must be positive number");
        this.houseId = houseId;
    }

    public boolean equalWithoutID(Address address) {
        return address.getCity().equals(city) && address.getStreet().equals(street)
                && address.getHouseId().equals(houseId) && address.getApartmentId().equals(apartmentId);
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        if (houseId < 0) throw new IllegalArgumentException("Negative apartment Id isn't allowed");
        this.apartmentId = apartmentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Address addressBuilder(List<String> params) {
        if (params.size() == 0) return null;
        return Address.builder()
                .city(params.size() > 0 && !params.get(0).equals("-") ? params.get(0) : null)
                .street(params.size() > 1 && !params.get(1).equals("-") ? params.get(1) : null)
                .houseId(params.size() > 2 && !params.get(2).equals("-")
                        ? Integer.parseInt(params.get(2)) : null)
                .apartmentId(params.size() > 3 && !params.get(3).equals("-")
                        ? Integer.parseInt(params.get(3)) : null)
                .build();

    }

    @Override
    public String toString() {
        return city + ", " + (street != null ? street : "") + " " + (houseId != null ? houseId : "") +
                (apartmentId != null ? ("/" + apartmentId) : "");
    }
}
