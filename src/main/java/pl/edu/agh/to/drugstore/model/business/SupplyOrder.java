package pl.edu.agh.to.drugstore.model.business;

import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
// set table name
public class SupplyOrder extends Order {

    @OneToOne
    @NonNull
    private Supplier supplier;

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
