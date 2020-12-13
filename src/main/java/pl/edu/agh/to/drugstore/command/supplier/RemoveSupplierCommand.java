package pl.edu.agh.to.drugstore.command.supplier;

import pl.edu.agh.to.drugstore.command.Command;
import pl.edu.agh.to.drugstore.model.business.Supplier;
import pl.edu.agh.to.drugstore.model.dao.SupplierDAO;

import java.util.List;

public class RemoveSupplierCommand implements Command {

    private final List<Supplier> suppliersToRemove;

    private final SupplierDAO supplierDAO;

    public RemoveSupplierCommand(List<Supplier> suppliersToRemove, SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
        this.suppliersToRemove = suppliersToRemove;
    }

    @Override
    public void execute() {
        suppliersToRemove.forEach(supplier -> supplierDAO.delete(supplier.getId()));
    }

    @Override
    public String getName() {
        return suppliersToRemove.size() > 1 ? "Removed " + suppliersToRemove.size() + " transactions"
                : "Removed transaction: " + suppliersToRemove.toString();
    }

    @Override
    public void undo() {
        suppliersToRemove.forEach(
                supplier -> {
                    Supplier newSupplier = new Supplier(supplier);
                    supplierDAO.add(newSupplier);
                });
    }

    @Override
    public void redo() {
        execute();
    }
}
