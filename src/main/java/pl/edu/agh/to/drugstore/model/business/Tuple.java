package pl.edu.agh.to.drugstore.model.business;

import javax.persistence.Embeddable;

// TODO
//  check persistence.xml

@Embeddable
public class Tuple {
    private int quantity;
    private boolean booked;

    public Tuple() {}

    public Tuple(int quantity, boolean booked) {
        this.quantity = quantity;
        this.booked = booked;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}
