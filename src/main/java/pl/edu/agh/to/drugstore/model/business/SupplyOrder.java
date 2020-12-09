package pl.edu.agh.to.drugstore.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
// set table name
public class SupplyOrder extends Order {

    @OneToOne
    @Column(nullable = false)
    private Supplier supplier;

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
